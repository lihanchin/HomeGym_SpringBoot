package edu.ntut.project_01.homegym.util;

import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

@Component
public class GlobalService {

    public static Blob fileToBlob(String imageFileName) throws IOException, SQLException {
        File imageFile = new File(imageFileName);
        long size = imageFile.length();
        byte[] b = new byte[(int) size];
        SerialBlob sb = null;
        try (FileInputStream fis = new FileInputStream(imageFile);) {
            fis.read(b);
            sb = new SerialBlob(b);
        }
        return sb;
    }

    public static Clob fileToClob(String textFileName) throws IOException, SQLException {
        Clob clob = null;
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(textFileName), "UTF-8");) {
            char[] c = new char[8192];
            StringBuffer buf = new StringBuffer();
            int len = 0;
            while ((len = isr.read(c)) != -1) {
                buf.append(new String(c, 0, len));
            }
            char[] ca = buf.toString().toCharArray();
            clob = new SerialClob(ca);
        }
        return clob;
    }

    public static void clobToFile(Clob clob, File file, String encoding) throws IOException, SQLException {
        try (Reader reader = clob.getCharacterStream();
             BufferedReader br = new BufferedReader(reader);
             FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
             PrintWriter out = new PrintWriter(osw);) {
            String line = null;
            while ((line = br.readLine()) != null) {
                out.println(line);
            }
        }
    }

    public static Blob fileToBlob(InputStream is, long size) throws IOException, SQLException {
        byte[] b = new byte[(int) size];
        SerialBlob sb = null;
        is.read(b);
        sb = new SerialBlob(b);
        return sb;
    }

}
