package com.hadoop.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	private static Set<String> processedURLs = new HashSet<String>();
	private static int count = 0;

	public static void main(String[] args) throws IOException {
		processPage(args[0]);
	}

	public static void processPage(String url) throws IOException {
			url = url.trim();
			/*if (processedURLs.contains(url)) {
				return;
			} else {*/
				if (processedURLs.size() < 1000) {
					processedURLs.add(url);
				} else {
					return;
				}
//			}

			// get useful information
			Document doc = Jsoup.connect(url).get();
//			if (doc.text().contains("terror")) {
				System.out.println(++count + "." + url);
				//TODO save page
				// get all links and recursively call the processPage method
				Elements questions = doc.select("a[href]");
				for (Element link : questions) {
					String ref = link.attr("abs:href");
					if (ref.startsWith("/wiki")) {
						ref = "https://en.wikipedia.org" + ref;
					}
					if (ref.startsWith("https://donate")) {
						continue;
					} else if (ref.contains("Wikipedia:")) {
						continue;
					} else if (ref.contains("File:")) {
						continue;
					} else if (ref.contains("Talk:")) {
						continue;
					} else if (ref.contains("Template:")) {
						continue;
					} else if (ref.contains("Media:")) {
						continue;
					} else if (ref.contains("Help:")) {
						continue;
					} else if (ref.contains("User:")) {
						continue;
					} else if (ref.contains("Template:")) {
						continue;
					} else if (ref.substring(9).contains(":")) {
						continue;
					} else if (ref.endsWith("-head")) {
						continue;
					} else if (ref.endsWith("-search")) {
						continue;
					} else if (ref.contains("#")) {
						continue;
					} else if (!ref.contains("terror")) {
						continue;
					}
					if (/*link.attr("href").contains("en.wikipedia") && */ ref.startsWith("https://en.wikipedia.org/wiki/") && !processedURLs.contains(ref) ) {
						processPage(ref);
					}
					/*if (link.attr("href").contains("en.wikipedia") && !processedURLs.contains(link.attr("abs:href")) ) {
						processPage(link.attr("abs:href"));
					}*/
				}
//			}

		}
	}
