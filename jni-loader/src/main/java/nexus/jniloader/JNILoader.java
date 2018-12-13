/*
 * Class NativeUtils is published under the The MIT License:
 *
 * Copyright (c) 2012 Adam Heinrich <adam@adamh.cz>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package nexus.jniloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Utility to load a binary library file inside a jar's resource directory.
 *
 * @implNote Modified and adapted from <a href=https://github.com/adamheinrich/native-utils>native-utils</a>.
 * @author Adam Heinrich
 * @author Tongfei Chen
 */
public class JNILoader {

    static String JNILIBS = "jnilibs";
    static String osName = System.getProperty("os.name");
    static String tmpDir = System.getProperty("java.io.tmpdir");

    public static URL getLibraryUrl(String libraryName) throws UnsupportedOperationException {
        URL url = null;
        if (osName.startsWith("Windows"))
            url = JNILoader.class.getResource("/" + libraryName + ".dll");
        else if (osName.startsWith("Mac")) {
            url = JNILoader.class.getResource("/lib" + libraryName + ".dylib");
            if (url == null)
                url = JNILoader.class.getResource("/lib" + libraryName + ".so");
            if (url == null)
                url = JNILoader.class.getResource("/lib" + libraryName + ".bundle");
        }
        else if (osName.startsWith("Linux"))
            url = JNILoader.class.getResource("/lib" + libraryName + ".so");

        if (url == null)
            throw new UnsupportedOperationException("Library " + libraryName + " not found.");
        else return url;
    }

    static File createTempDir() {
        File f = new File(tmpDir + "/" + JNILIBS);
        f.mkdir();
        return f;
    }

    public static void loadLibraryFromJar(String libraryName) throws IOException {

        File tempDir = createTempDir();
        URL url = getLibraryUrl(libraryName);
        String fileName = new File(url.getPath()).getName();
        File lib = new File(tempDir, fileName);

        try (InputStream is = getLibraryUrl(libraryName).openStream()) {
            Files.copy(is, lib.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        try {
            System.load(lib.getAbsolutePath()); // JVM requires absolute path
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        System.err.println("Native library " + libraryName + " loaded.");

    }

}
