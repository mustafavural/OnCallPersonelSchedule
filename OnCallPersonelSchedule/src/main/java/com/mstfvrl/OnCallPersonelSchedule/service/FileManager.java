package com.mstfvrl.OnCallPersonelSchedule.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.SneakyThrows;

public class FileManager {

	private static FileManager instance;

	private FileManager() {
		if (new File(StaticPrimitives.CONFIG_FILE).exists()) {
			StaticPrimitives.setSettings(readSettingFromXML());
		} else {
			StaticPrimitives.setSettings(new Settings());
			saveSettingsToXML(StaticPrimitives.getSettings());
		}
	}

	public static FileManager createInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}

	@SneakyThrows
	private Settings readSettingFromXML() {
		File xmlFile = new File(StaticPrimitives.CONFIG_FILE);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();
		Element root = (Element) doc.getElementsByTagName("Settings").item(0);

		int onCallPersonelCount = Integer.parseInt(root.getElementsByTagName("ekipBuyuklugu").item(0).getTextContent());
		int offDayCount = Integer.parseInt(root.getElementsByTagName("istirahatGunu").item(0).getTextContent());

		var dayPointString = Arrays.stream(root.getElementsByTagName("gunPuanlari").item(0).getTextContent().split(","))
				.toList();

		List<Integer> dayPoints = new ArrayList<Integer>();

		dayPointString.forEach(item -> dayPoints.add(Integer.parseInt(item)));

		List<String> names = Arrays.stream(doc.getElementsByTagName("Personels").item(0).getTextContent().split(","))
				.toList();

		return new Settings(onCallPersonelCount, offDayCount, dayPoints, names);
	}

	@SneakyThrows
	public void saveSettingsToXML(Settings settings) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();

		Element rootElement = doc.createElement("Configuration");
		doc.appendChild(rootElement);

		Element settingsElement = doc.createElement("Settings");
		rootElement.appendChild(settingsElement);

		Element ekipBuyuklugu = doc.createElement("ekipBuyuklugu");
		ekipBuyuklugu.appendChild(doc.createTextNode(String.valueOf(settings.getOnCallPersonelCount())));
		settingsElement.appendChild(ekipBuyuklugu);

		Element istirahatGunu = doc.createElement("istirahatGunu");
		istirahatGunu.appendChild(doc.createTextNode(String.valueOf(settings.getOffDayCount())));
		settingsElement.appendChild(istirahatGunu);

		Element gunPuanlari = doc.createElement("gunPuanlari");
		var asdf = settings.getDayPoints().toArray(Integer[]::new);
		gunPuanlari.appendChild(doc.createTextNode(Arrays.stream(asdf).mapToInt(Integer::valueOf)
				.mapToObj(String::valueOf).collect(Collectors.joining(","))));
		settingsElement.appendChild(gunPuanlari);

		Element personelsElement = doc.createElement("Personels");
		personelsElement.appendChild(doc.createTextNode(String.join(",", settings.getNames())));
		rootElement.appendChild(personelsElement);

		// XML dosyasını kaydet
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(StaticPrimitives.CONFIG_FILE));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
	}

	@SneakyThrows
	public void exportOnCallListToExcel(List<List<String>> dgvNobet, List<List<String>> dgvPuan, String saveLocation) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet1 = workbook.createSheet(StaticPrimitives.getSelectedMonthName() + " Ayı Nöbet Çizelgesi");
			Sheet sheet2 = workbook.createSheet(StaticPrimitives.getSelectedMonthName() + " Ayı Puan Çizelgesi");

			// Nöbet sayfası için satır ve sütun verilerini ekleme
			for (int j = 0; j < dgvNobet.size(); j++) {
				Row headerRow = sheet1.createRow(0);
				headerRow.createCell(j).setCellValue(dgvNobet.get(j).get(0));
				for (int i = 0; i < dgvNobet.get(j).size(); i++) {
					sheet1.createRow(i + 1).createCell(j).setCellValue(dgvNobet.get(i).get(j).toString());
				}
			}

			// Puan tablosu için satır ve sütun verilerini ekleme
			for (int j = 0; j < dgvPuan.size(); j++) {
				Row headerRow = sheet2.createRow(0);
				headerRow.createCell(j).setCellValue(dgvPuan.get(j).get(0));
				for (int i = 0; i < dgvPuan.get(j).size(); i++) {
					sheet2.createRow(i + 1).createCell(j).setCellValue(dgvPuan.get(i).get(j).toString());
				}
			}

			try (FileOutputStream fos = new FileOutputStream(saveLocation)) {
				workbook.write(fos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
