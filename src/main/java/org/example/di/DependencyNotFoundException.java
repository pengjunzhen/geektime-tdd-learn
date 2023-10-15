package org.example.di;

public class DependencyNotFoundException extends RuntimeException {

    private Class<?> compenent;
    private Class<?> dependency;

    public DependencyNotFoundException(Class<?> component, Class<?> dependency) {
        this.compenent = component;
        this.dependency = dependency;
    }

    public Class<?> getDependency() {
        return dependency;
    }

    public Class<?> getComponent() {
        return compenent;
    }
}
