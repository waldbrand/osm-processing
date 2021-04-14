package de.waldbrand.app.osm.processing;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import de.topobyte.system.utils.SystemPaths;

public class ExtractLandkreise
{

	public static void main(String[] args) throws IOException,
			ParserConfigurationException, SAXException, TransformerException
	{
		if (args.length != 1) {
			System.out.println("usage: extract-landkreise <osm-file.tbo>");
			System.exit(1);
		}

		Path input = Paths.get(args[0]);

		Path dirData = SystemPaths.CWD.resolve("data");
		Path dirKreise = dirData.resolve("kreise");

		RegionExtractor regionExtractor = new RegionExtractor(input);
		regionExtractor.prepare();
		regionExtractor.extract(dirKreise, tags -> {
			String adminLevel = tags.get("admin_level");
			if (!"6".equals(adminLevel)) {
				return false;
			}
			return true;
		});
	}

}
