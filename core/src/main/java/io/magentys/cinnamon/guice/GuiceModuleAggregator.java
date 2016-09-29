package io.magentys.cinnamon.guice;

import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.Arrays.asList;

public class GuiceModuleAggregator {

    private final List<Class<? extends Annotation>> moduleAnnotations;
    private final List<Class<? extends Annotation>> overridesModuleAnnotations;
    private final List<String> packages;

    public GuiceModuleAggregator(Builder builder) {
        this.moduleAnnotations = builder.moduleAnnotations;
        this.overridesModuleAnnotations = builder.overridesModuleAnnotations;
        this.packages = builder.packages;
    }

    public List<Class<? extends Annotation>> getModuleAnnotations() {
        return moduleAnnotations;
    }

    public List<Class<? extends Annotation>> getOverridesModuleAnnotations() {
        return overridesModuleAnnotations;
    }

    public List<String> getPackages() {
        return packages;
    }

    public static class Builder {
        private static final String GUICE_PACKAGE = "com.google.inject";
        private final Logger logger = LoggerFactory.getLogger(getClass());
        private List<Class<? extends Annotation>> moduleAnnotations = new ArrayList<>();
        private List<Class<? extends Annotation>> overridesModuleAnnotations = new ArrayList<>(Collections.singletonList(OverridesModule.class));
        private List<String> packages = new ArrayList<>();

        @SuppressWarnings("unchecked")
        public Builder withModuleAnnotations(Class<? extends Annotation>... moduleAnnotations) {
            this.moduleAnnotations = new ArrayList<>(asList(moduleAnnotations));
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder withOverrideModuleAnnotations(Class<? extends Annotation>... overridesModuleAnnotations) {
            this.overridesModuleAnnotations = new ArrayList<>(asList(overridesModuleAnnotations));
            return this;
        }

        public Builder withPackages(String... packages) {
            this.packages = new ArrayList<>(asList(packages));
            return this;
        }

        public Module build() {
            try {
                Map<String, Module> modules = getModulesAnnotatedWith(moduleAnnotations);
                Map<String, Module> overridesModules = getModulesAnnotatedWith(overridesModuleAnnotations);
                modules = modules.isEmpty() ? getSubTypesOfModule() : modules;
                logger.debug("Modules [" + Collections.singletonList(modules.keySet()) + "]");
                logger.debug("Overrides modules [" + (overridesModules.isEmpty() ? "" : Collections.singletonList(overridesModules.keySet())) + "]");
                return overridesModules.isEmpty() ?
                        Modules.combine(modules.values()) :
                        Modules.override(modules.values()).with(overridesModules.values());
            } catch (FileSystemException e) {
                logger.error(e.getMessage());
            }
            return null;
        }

        private Map<String, Module> getModulesAnnotatedWith(List<Class<? extends Annotation>> annotations) throws FileSystemException {
            Map<String, Module> modules = new HashMap<>();
            packages = packages.isEmpty() ? getPackages() : packages;
            Reflections reflections = new Reflections(packages);
            for (Class<? extends Annotation> annotation : annotations) {
                reflections.getTypesAnnotatedWith(annotation).stream().filter(Module.class::isAssignableFrom).forEachOrdered(clazz -> {
                    try {
                        Module instance = (Module) clazz.newInstance();
                        if (!modules.containsKey(clazz.getName()))
                            modules.put(clazz.getName(), instance);
                    } catch (InstantiationException | IllegalAccessException e) {
                        logger.error(e.getMessage());
                    }
                });
            }
            return modules;
        }

        private Map<String, Module> getSubTypesOfModule() throws FileSystemException {
            Map<String, Module> modules = new HashMap<>();
            packages = packages.isEmpty() ? getPackages() : packages;
            // To getEventBus a type within the scan result that has some super types,
            // one of which implements/extends the relevant interface/class,
            // then you have to include the packages in which the super types
            // reside.
            Reflections reflections = new Reflections(packages, GUICE_PACKAGE);
            reflections.getSubTypesOf(Module.class).stream()
                    .filter(module -> !hasOverridesAnnotation(module) && !module.getName().startsWith(GUICE_PACKAGE) && !module.getName()
                            .equals(CinnamonModule.class.getName())).forEachOrdered(module -> {
                try {
                    Module instance = module.newInstance();
                    if (!modules.containsKey(module.getName()))
                        modules.put(module.getName(), instance);
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error(e.getMessage());
                }
            });
            return modules;
        }

        private boolean hasOverridesAnnotation(Class<? extends Module> module) {
            for (Class<? extends Annotation> annotation : overridesModuleAnnotations) {
                if (module.isAnnotationPresent(annotation))
                    return true;
            }
            return false;
        }

        private List<String> getPackages() throws FileSystemException {
            FileSystemManager fileSystemManager = VFS.getManager();
            HashSet<String> packageNames = new HashSet<>();
            for (String element : System.getProperty("java.class.path").split(";")) {
                if (element.endsWith("jar")) {
                    FileObject fileObject = fileSystemManager.resolveFile("jar://" + element);
                    addPackages(fileObject, "", packageNames);
                } else {
                    FileObject fileObject = fileSystemManager.resolveFile(element);
                    if (fileObject.getType() == FileType.FOLDER)
                        addPackages(fileObject, fileObject.getName().getPath(), packageNames);
                }
            }
            return new ArrayList<>(packageNames);
        }

        private void addPackages(FileObject fileObject, String filePath, HashSet<String> packageNames) throws FileSystemException {
            for (FileObject child : fileObject.getChildren()) {
                if (!child.getName().getBaseName().equals("META-INF")) {
                    if (child.getType() == FileType.FOLDER) {
                        addPackages(child, filePath, packageNames);
                    } else if (child.getName().getExtension().equals("class")) {
                        String parentPath = child.getParent().getName().getPath();
                        parentPath = StringUtils.remove(parentPath, filePath);
                        parentPath = StringUtils.removeStart(parentPath, "/");
                        parentPath = parentPath.replaceAll("/", ".");
                        packageNames.add(parentPath);
                    }
                }
            }
        }
    }
}
