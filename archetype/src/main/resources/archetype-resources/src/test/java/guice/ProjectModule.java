#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.guice;

import com.google.inject.AbstractModule;

public final class ProjectModule extends AbstractModule {
    @Override
    public void configure() {
        try {
            // Bindings for classes that are shared for the lifetime of the
            // scenario.
        } catch (Exception e) {
            addError(e.getMessage());
        }
    }
}
