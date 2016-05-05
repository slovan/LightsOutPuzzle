package com.lightsout.language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public enum LanguageSwitcher {
    DEFAULT, EN, RU("langs/russian.lng"), SK("langs/slovak.lng");

    private File fr;
    private HashMap<String, String> langScheme;

    LanguageSwitcher() {
        this.langScheme = IDefaultLanguage.getDefaultLanguageScheme();
        this.fr = null;
        if (this.toString().equals("DEFAULT")) {
            try (BufferedReader br = new BufferedReader(new FileReader("langs/config.conf"))) {
                String str = br.readLine();
                String[] data = str.split("=");
                if (data[0].equals("currentLanguage")) {
                    switch (data[1].toUpperCase()) {
                        case "RU":
                            this.fr = new File("langs/russian.lng");
                            this.obtainScheme();
                            break;
                        case "SK":
                            this.fr = new File("langs/slovak.lng");
                            this.obtainScheme();
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    LanguageSwitcher(String path) {
        this.fr = new File(path);
        if (!this.fr.exists()) {
            this.fr.getParentFile().mkdirs();
            try {
                this.fr.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.langScheme = IDefaultLanguage.getDefaultLanguageScheme();
        this.obtainScheme();
    }

    public HashMap<String, String> getLangScheme() {
        return this.langScheme;
    }

    private void obtainScheme() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.fr))) {
            String str;
            while ((str = br.readLine()) != null) {
                String[] data = str.split("=");
                this.langScheme.replace(data[0], data[1]);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isCurrent() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("langs/config.conf"))) {
            String str = br.readLine();
            String[] data = str.split("=");
            if (data[0].equals("currentLanguage")) {
                return data[1].equals(this.toString());
            } else {
                throw new IOException("Error: incorrect format of data in \"config.conf\"");
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Error: file \"config.conf\" was not found");
        }
    }
}
