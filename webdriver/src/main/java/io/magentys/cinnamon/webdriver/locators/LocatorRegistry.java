package io.magentys.cinnamon.webdriver.locators;

import com.google.inject.Inject;
import io.magentys.cinnamon.conf.ConfigConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.By.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static java.util.Arrays.asList;

public class LocatorRegistry {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<LocatorType> locatorTypes = asList(LocatorType.ID, LocatorType.NAME, LocatorType.CSS, LocatorType.XPATH, LocatorType.TAG_NAME);
    private final Map<String, By> registry = new HashMap<>();
    private final Properties properties;

    @Inject
    public LocatorRegistry() {
        this(System.getProperties(), (File[]) null);
    }

    LocatorRegistry(Properties properties, File... files) {
        this.properties = properties;
        files = (files == null) ? getLocatorFiles() : files;
        init(files);
    }

    private void init(File... files) {
        for (File file : files) {
            try (FileInputStream fis = new FileInputStream(file)) {
                load(fis);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new LocatorException(e);
            }
        }
    }

    public By getLocator(String key) {
        if (!registry.containsKey(key))
            throw new LocatorException("Cannot find the locator with key [" + key + "]");
        return registry.get(key);
    }

    @SuppressWarnings("unchecked")
    private void load(InputStream inputStream) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = (Map<String, Object>) yaml.load(inputStream);
            load(data);
        } catch (YAMLException e) {
            logger.error(e.getMessage(), e);
            throw new LocatorException(e.getMessage());
        }
    }

    private void load(Map<String, Object> data) {
        Map<String, Object> flattenedData = flatten(data);
        for (LocatorType locatorType : locatorTypes) {
            registerLocators(locatorType, flattenedData);
        }
    }

    @SuppressWarnings("unchecked")
    private void registerLocators(LocatorType locatorType, Map<String, Object> data) {
        if (!data.containsKey(locatorType.name().toLowerCase()))
            return;
        if (data.get(locatorType.name().toLowerCase()) == null)
            return;
        for (Map.Entry<String, String> entry : ((Map<String, String>) data.get(locatorType.name().toLowerCase())).entrySet()) {
            try {
                Class<?> clazz = Class.forName(locatorType.getClassName());
                Constructor<?> constructor = clazz.getConstructor(String.class);
                By by = (By) constructor.newInstance(entry.getValue());
                registry.put(entry.getKey(), by);
            } catch (ClassCastException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                logger.error(e.getMessage(), e);
                throw new LocatorException(e);
            }
        }
    }

    private File[] getLocatorFiles() {
        Collection<File> files = FileUtils.listFiles(new File(ConfigConstants.PROJECT_DIR), new RegexFileFilter("(.+)?locator.ya?ml$"), TrueFileFilter.INSTANCE);
        if (files.isEmpty())
            throw new LocatorException("A filename matching either locator.yml or locator.yaml could not be found.");
        return files.stream().toArray(File[]::new);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> flatten(Map<String, Object> data) {
        Map.Entry<?, ?> parent = ((Map<?, ?>) data).entrySet().iterator().next();
        Map.Entry<?, ?> child = ((Map<?, ?>) parent.getValue()).entrySet().iterator().next();
        return (child.getValue() instanceof String) ? data : (Map<String, Object>) data.get(properties.getProperty("locator", "default"));
    }

    enum LocatorType {
        ID(ById.class), NAME(ByName.class), CSS(ByCssSelector.class), XPATH(ByXPath.class), TAG_NAME(ByTagName.class);

        private final Class<?> clazz;

        LocatorType(final Class<?> clazz) {
            this.clazz = clazz;
        }

        public String getClassName() {
            return clazz.getName();
        }
    }

}
