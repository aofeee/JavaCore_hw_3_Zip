package com.netology.homework.zipsave;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(5, 3, 4, 124.5);
        GameProgress gameProgress2 = new GameProgress(6, 4, 5, 354.5);
        GameProgress gameProgress3 = new GameProgress(7, 5, 6, 798.5);

        String pathFile = "D://Games/savegames/save.dat";
        saveGame(pathFile, gameProgress1);
        saveGame(pathFile, gameProgress2);
        saveGame(pathFile, gameProgress3);

        String pathZip = "D://Games/savegames/zip.zip";
        zipFiles(pathFile, pathZip);

        String pathFile2 = "D://Games/savegames/";
//        openZip(pathZip, pathFile2);
    }

    private static void saveGame(String pathFile, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathFile.replace('/', File.separatorChar), true))) {
            oos.writeObject(gameProgress);
            oos.writeObject("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(String pathFile, String pathZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip.replace('/', File.separatorChar), true));
             FileInputStream fis = new FileInputStream(pathFile.replace('/', File.separatorChar))) {
            ZipEntry entry = new ZipEntry("packed_save.dat");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File deleteFile = new File(pathFile.replace('/', File.separatorChar));
        deleteFile.delete();
    }

    private static void openZip(String pathZip, String pathFile2) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip.replace('/', File.separatorChar)))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathFile2.replace('/', File.separatorChar) + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
