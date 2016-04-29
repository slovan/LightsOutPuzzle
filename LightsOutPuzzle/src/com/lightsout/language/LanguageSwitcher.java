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
	private HashMap<String,String> langScheme;
	
	private LanguageSwitcher() {
		if (this.toString().equals("EN")) {
			this.fr = null;
			this.langScheme = DefaultLanguage.getDefaultLanguageScheme();
		} else if (this.toString().equals("DEFAULT")) {
			try (BufferedReader br = new BufferedReader(new FileReader("langs/config.conf"))) {
				String str = br.readLine();
				String[] data = str.split("=");
				if (data[0].equals("currentLanguage")) {
					data[1] = data[1].toUpperCase();
					if (data[1].equals("DEFAULT") || data[1].equals("EN")) {
						this.fr = null;
						this.langScheme = DefaultLanguage.getDefaultLanguageScheme();
					} else {
						LanguageSwitcher ls = LanguageSwitcher.valueOf(data[1]);
						this.fr = ls.fr;
						this.langScheme = ls.langScheme;
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
	private LanguageSwitcher(String path) {
		this.fr = new File(path);
		if (!fr.exists()) {
			fr.getParentFile().mkdirs();
			try {
				fr.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.langScheme = DefaultLanguage.getDefaultLanguageScheme();
		obtainScheme();
	}
	
	public HashMap<String, String> getLangScheme() {
		return langScheme;
	}
	
	private void obtainScheme() {
		try (BufferedReader br = new BufferedReader(new FileReader(fr))) {
			String str;
			while((str = br.readLine()) != null) {
				String[] data = str.split("=");
				System.out.println(data[1]);
				langScheme.replace(data[0], data[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isCurrent(LanguageSwitcher langSw) {
		try (BufferedReader br = new BufferedReader(new FileReader("langs/config.conf"))) {
			String str = br.readLine();
			String[] data = str.split("=");
			if (data[0].equals("currentLanguage")) {
				if (data[1].equals(langSw.toString()))
					return true;
				else
					return false;
			} else {
				/////Add Exception
				System.out.println("Error format of \"config.conf\"");
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
