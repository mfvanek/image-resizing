rootProject.name = "image-resizing"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("checkstyle", "10.15.0")
            version("pmd", "7.0.0")
            version("jacoco", "0.8.12")
            version("lombok", "1.18.32")
        }
    }
}
