package cdp.troubleshooting.memoryleak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {

	final ClassLoader parentClassLoader;

	public CustomClassLoader(ClassLoader parentClassLoader) {
		this.parentClassLoader = parentClassLoader;
	}

	@Override
	public Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
		if (!className.startsWith("cdp.troubleshooting.memoryleak")) {
			return super.loadClass(className, resolve);
		}
		try {
			String classAsPath = "bin/" + className.replace('.', '/') + ".class";
			Path path = Paths.get(classAsPath);
			byte[] classBytes = Files.readAllBytes(path);
			Class<?> c = defineClass(className, classBytes, 0, classBytes.length);
			if (resolve) {
				resolveClass(c);
			}
			return c;
		} catch (IOException ex) {
			throw new ClassNotFoundException("Could not load " + className, ex);
		}
	}

}
