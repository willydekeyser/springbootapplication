package willydekeyser.loggers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class FileLoggers {

	public void schrijfDataToFile(String data) throws IOException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String fileName = "logfiles/Logindata" + year + ".log";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.newLine();
		writer.append("Datum: " + dateFormat.format(new Date()));
		writer.newLine();
		writer.append(data);
		writer.newLine();
		writer.close();
	}
	
	public void schrijfDataToJson(Object data) throws IOException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String fileName = "logfiles/Logindata" + year + ".json";
		FileWriter fileWriter = new FileWriter(fileName, true);
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		gson.toJson(data, fileWriter);
		fileWriter.close();
	}
	
	public void schrijfAgendaToFile(String data) throws IOException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String fileName = "logfiles/Agendadata" + year + ".log";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.newLine();
		writer.append("Datum: " + dateFormat.format(new Date()));
		writer.newLine();
		writer.append(data);
		writer.newLine();
		writer.close();
	}
	
	public void schrijfLidgeldToFile(String data) throws IOException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String fileName = "logfiles/Lidgelddata" + year + ".log";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.newLine();
		writer.append("Datum: " + dateFormat.format(new Date()));
		writer.newLine();
		writer.append(data);
		writer.newLine();
		writer.close();
	}
}


