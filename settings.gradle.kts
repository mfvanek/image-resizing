rootProject.name = "image-resizing"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("checkstyle", "10.12.0")
            version("pmd", "6.55.0")
            version("jacoco", "0.8.10")
            version("lombok", "1.18.28")
        }
    }
}
