package io.quarkiverse.mongock.runtime.graal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;

import org.reflections.Reflections;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

/**
 * Get rid of JBoss VFS if it is not present in the classpath.
 */
@TargetClass(value = Reflections.class, onlyWith = OrgReflectionsSubstitutions.IsJBossVFSAbsent.class)
public final class OrgReflectionsSubstitutions {

    // Only necessary for package scanning, not used with Quarkus
    @Substitute
    private Map<String, Map<String, Set<String>>> scan() {
        return new HashMap<>();
    }

    static final class IsJBossVFSAbsent implements BooleanSupplier {

        @Override
        public boolean getAsBoolean() {
            try {
                Class.forName("org.jboss.vfs.VFS");
                return false;
            } catch (ClassNotFoundException e) {
                return true;
            }
        }
    }
}
