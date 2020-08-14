package tsg.luckpermslock.file;

import java.io.File;
import java.io.IOException;

import tsg.luckpermslock.main.LuckPermsLock;

public class FileManager {

	public File list;

	public void setup() {
		list = new File(LuckPermsLock.instance.getDataFolder(), "list.yml");
		if (!list.exists()) {
			try {
				list.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
