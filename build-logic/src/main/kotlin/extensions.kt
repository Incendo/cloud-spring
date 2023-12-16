import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

public val Project.libs: LibrariesForLibs
    get() = the()
