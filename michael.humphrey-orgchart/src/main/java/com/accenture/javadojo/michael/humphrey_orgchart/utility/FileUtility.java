package com.accenture.javadojo.michael.humphrey_orgchart.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class that handles searching for file and converting into a buffered stream
 *
 * @author michael.humphrey
 *
 */
public final class FileUtility {

    private FileUtility() {

        // private constructor to avoid class being instantiated by accident
    }

    /**
     * Open file by path name
     *
     * @param filePath the file path to open
     * @return BufferedReader resulting from finding file and wrapping with InputStreamReader and
     *         BufferedReader
     */
    public static final BufferedReader findFileFromPath(String filePath)
        throws IOException {

        InputStream is =
            FileUtility.class.getClassLoader().getResourceAsStream(filePath);

        if (is == null) {
            throw new IOException("File could not be found at " + filePath);
        }

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        return br;
    }
}
