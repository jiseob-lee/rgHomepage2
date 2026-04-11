package com.rg.goAccess.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rg.goAccess.dto.GoAccessDTO;
import com.rg.goAccess.filter.GoAccessFileFilter;
//import com.rg.goAccess.jni.GoAccessJNI;
import com.rg.goAccess.repeater.Repeater;

@Controller
public class GoAccessController {

	private final Logger logger = LogManager.getLogger(GoAccessController.class);
	
	private String currentTime = "";
	
	private String key = "GoAccessCurrentTime";
	
	//@Autowired
	//RedisTemplate<String, Object> redisTemplate;

	//@Value("${goaccessPath}")
	//private String path;
	//private static String goaccessPath;
	
	//private static String goaccessPath2 = "/home/ubuntu/apache-tomcat-8.5.45_2/webapps/ROOT/goaccess";
	//private String goaccessPath = "/home/ubuntu/goaccess";

	@Value("${apachePath}")
	private String apachePath;


	//@PostConstruct
	//public void init() {
		//goaccessPath = path;
	//}
	
	@RequestMapping(value="/rg/getGoaccessCurrentTime.do")
	@ResponseBody
	public String getGoaccessCurrentTime() {
		
		//ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();
		
	    //currentTime = (String) stringStringValueOperations.get(key);
	    
		if ("".equals(currentTime)) {
			File folder = new File(ConfigHolder.goaccessPath);
			File[] files = folder.listFiles();
			if (files != null) { //some JVMs return null for empty dirs
				for (File f: files) {
					if (f.isDirectory()) {
						//emptyFolder(f);
					} else {
						//f.delete();
						currentTime = f.getName().substring(0, f.getName().indexOf("."));
						System.out.println("######################### currentTime : " + currentTime);

						//stringStringValueOperations.set(key, currentTime); // redis set 명령어

						break;
					}
				}
			}
		}
		
		return currentTime;
	}

	@RequestMapping(value="/rg/checkGoaccessResultFile.do")
	@ResponseBody
	public Integer checkGoaccessResultFile() {
		
		int result = 0;
		
		Repeater.resetNo();
		
		try {
			result = Repeater.repeat();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value="/rg/goAccessParse.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> goProcessParse(GoAccessDTO goAccessDTO) {
		
		String goaccessCommand = "/usr/local/bin/goaccess --geoip-database=/home/ubuntu/GeoLite2/GeoLite2-City.mmdb --config-file=/usr/local/etc/goaccess/goaccess.conf ";

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		currentTime = dateFormat.format(date);
		
		//ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();
		
	    //stringStringValueOperations.set(key, currentTime); // redis set 명령어

		//logger.debug("############################ FromDate : " + goAccessDTO.getFromDate());
		//logger.debug("############################ ToDate : " + goAccessDTO.getToDate());
		logger.info("#### FromDate : " + goAccessDTO.getFromDate());
		logger.info("#### ToDate : " + goAccessDTO.getToDate());
		
		List<LocalDate> datesList = getDatesBetweenTerms(goAccessDTO);
		//for (int i=0; i < datesList.size(); i++) {
			//logger.debug("########## " + datesList.get(i).toString());
		//}
		
		//logger.debug("##########");
		//logger.debug("##########");
		//logger.debug("##########");
		//logger.debug("##########");
		
		List<String> accessLogFiles = getAccessLogFileList(datesList);
		for (int i=0; i < accessLogFiles.size(); i++) {
			//logger.debug("########## " + accessLogFiles.get(i));
			goaccessCommand += accessLogFiles.get(i) + " ";
		}
		
		goaccessCommand += " --exclude-ip 127.0.0.1 -o " + ConfigHolder.goaccessPath + "/" + currentTime + ".html";
				
		//return 0;
		
		
		//jisblee.me-ssl-access_log.2018-10-21-00.log
		//jisblee.me-access_log.2018-10-21-00.log
		
		//"zcat -f /var/log/apache2/access.log* | goaccess -a -o report.jsp";
		
		//goaccess /usr/local/apache2/logs/*access_log.2018-10-20-* /usr/local/apache2/logs/*access_log.2018-10-21-* -a -o report.html
		
		String [] commandsArray = new String[datesList.size() + 2];
		//String [] commandsArray = new String[4];
		
		commandsArray[0] = "goaccess";
		
		for (int i=0; i < datesList.size(); i++) {
			commandsArray[1 + i] = apachePath + "/logs/jisblee.me-ssl-access_log." + datesList.get(i).toString() + "-00.log";
		}
		
		//commandsArray[2] = "/usr/local/apache2/logs/jisblee.me-access_log.2018-10-21-00.log";

		commandsArray[datesList.size() + 1] = "-o " + ConfigHolder.goaccessPath + "/report.html";

		//commandsArray[2] = "-a";
		
		//commandsArray[1] = "--real-time-html";
		//commandsArray[1] = "--ssl-cert=/home/ubuntu/private_certs/jisblee.me_20180107B19T.crt.pem";
		//commandsArray[2] = "--ssl-key=/home/ubuntu/private_certs/jisblee.me_20180107B19T.key.pem";
		//commandsArray[1] = "/usr/local/apache2/logs/jisblee.me-ssl-access_log.2018-10-21-00.log";
		//commandsArray[2] = "--debug-file==/home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess/debug.html";
		//commandsArray[3] = "-o /home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess/report54321.html";
		
		//System.out.println("######################### 1 ########################");
		//for (int i = 0; i < commandsArray.length; i++) {
			//System.out.print(commandsArray[i] + " ");
		//}
		//System.out.println();
		//System.out.println("######################### 2 ########################");
		
		/*
		List<String> cmd = new ArrayList<String>();
		cmd.add("goaccess");

		//for (int i=0; i < datesList.size(); i++) {
			//cmd.add("/usr/local/apache2/logs/*access_log." + datesList.get(i).toString() + "*");
		//}
		
		//cmd.add("/usr/local/apache2/logs/jisblee.me-ssl-access_log.2018-10-21-00.log");
		//cmd.add("/usr/local/apache2/logs/jisblee.me-access_log.2018-10-21-00.log");

		//cmd.add("-a");
		//cmd.add("-o");
		//cmd.add("/home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess/report.html");
		*/
		
		//return 0;
		
		/*
        try {
            // Command to create an external process
            //String command = "goaccess /usr/local/apache2/logs/jisblee.me-ssl-access_log.2018-10-21-00.log -o /home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess/report54321.html " + 
            " --log-format=COMBINED --date-format=%d/%b/%Y --time-format=%H:%M:%S ";
            //"--log-format=COMBINED --date-format='%d/%b/%Y' --time-format='%H:%M:%S'";
  
            // Running the above command
            Runtime run  = Runtime.getRuntime();
            run.exec(command);
            
            //Process process11 = run.exec(command);

        } catch (IOException e) { 
            e.printStackTrace();
        }
        */
        
        /*
		try {
			Process process11 = new ProcessBuilder(commandsArray).start();

			
			BufferedReader stdOut = new BufferedReader( new InputStreamReader(process11.getInputStream()) );
			String str = "";
			
			// 표준출력 상태를 출력
			while( (str = stdOut.readLine()) != null ) {
			    System.out.println(str);
			}

			BufferedReader stdError = new BufferedReader(new InputStreamReader(process11.getErrorStream()));
			while( (str = stdError.readLine()) != null ) {
			    System.out.println(str);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		//int returnVal = new GoAccessJNI().parse();
        
		//return returnVal;
		
		

		
		//String [] commandsArray2 = {"goaccess", "/usr/local/apache2/logs/jisblee.me-ssl-access_log.2018-10-21-00.log", "-a", "-o", "/home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess/report3.html"};
		//String [] commandsArray2 = new String[]{"goaccess", "-f /usr/local/apache2/logs/jisblee.me-ssl-access_log.2018-10-21-00.log", "-a",
				//"-o /home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess/report4321.html"};
		
		/*
		String [] commandsArray2 = {"/home/ubuntu/c/a.out"};
		
		ProcessBuilder builder = new ProcessBuilder();
		//if (isWindows) {
		    //builder.command("cmd.exe", "/c", "dir");
		//} else {
		    builder.command(commandsArray2);
		//}
		//builder.directory(new File(System.getProperty("user.home")));
		//builder.directory(new File("/home/ubuntu/apache-tomcat-8.5.34/webapps/ROOT/goaccess"));
		//builder.directory(new File("/usr/local/apache2/logs"));
			
		Process process;
		int exitCode = 0;
		try {
			process = builder.start();
			exitCode = process.waitFor();
			
			BufferedReader stdOut = new BufferedReader( new InputStreamReader(process.getInputStream()) );
			String str = "";
			
			// 표준출력 상태를 출력
			while( (str = stdOut.readLine()) != null ) {
			    System.out.println(str);
			}

			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while( (str = stdError.readLine()) != null ) {
			    System.out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//StreamGobbler streamGobbler = 
		  //new StreamGobbler(process.getInputStream(), System.out::println);
		//Executors.newSingleThreadExecutor().submit(streamGobbler);
		//int exitCode = process.waitFor();
		//assert exitCode == 0;
		return exitCode;
		*/

		emptyFolder(new File("/home/ubuntu/cgi-bin"));
		
		String path = "/home/ubuntu/cgi-bin/";
		String filename = currentTime + ".cgi";
		
		File file = new File(path + filename);

		
		try {

			file.getParentFile().mkdirs();
			
			if (!file.exists()) {
				file.createNewFile();
			}

			file.setReadable(true, false);
			file.setExecutable(true, false);
			file.setWritable(true, false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		List<String> fileContent = new ArrayList<String>();
		fileContent.add("#!/bin/bash");
		fileContent.add("");
		fileContent.add("echo \"Content-type: text/html\"");
		fileContent.add("echo \"\"");
		fileContent.add("echo \"Hello.<br/>\"");
		fileContent.add("cd " + apachePath + "/logs");
		fileContent.add(goaccessCommand);
		//fileContent.add("cp " + goaccessPath + "/" + currentTime + ".html " + goaccessPath2 + "/" + currentTime + ".html");
		//fileContent.add("echo \"Done.<br/>\"");
		
		try {

			FileOutputStream fos = new FileOutputStream(file);
			 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		 
			for (int i = 0; i < fileContent.size(); i++) {
				bw.write(fileContent.get(i));
				bw.newLine();
			}
		 
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		file.setWritable(false, false);
		
		emptyFolder(new File(ConfigHolder.goaccessPath));
		
		/*
		try {
			URL myURL = new URL("https://www.jisblee.me/cgi-bin/" + currentTime + ".cgi");
			
			HttpsURLConnection myURLConnection = (HttpsURLConnection)myURL.openConnection();
			myURLConnection.connect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", "https://www.jisblee.me/cgi-bin/" + currentTime + ".cgi");
		return map;
	}
	
	private void emptyFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { //some JVMs return null for empty dirs
			for (File f: files) {
				if (f.isDirectory()) {
					emptyFolder(f);
				} else {
					f.delete();
				}
			}
		}
		//folder.delete();
	}
	
	private List<String> getAccessLogFileList(List<LocalDate> datesList) {
		List<String> files = new ArrayList<String>();
        
		File dir = new File(apachePath + "/logs");

		for (int i=0; i<datesList.size(); i++) {
			LocalDate date = datesList.get(i);
			
			GoAccessFileFilter goAccessFileFilter = new GoAccessFileFilter(date);
			      
			for (File file : dir.listFiles(goAccessFileFilter)) {
				if (file.length() > 0) {
					files.add(file.getName());
				}
			}
		}
		
		return files;
	}

	@RequestMapping(value="/goAccessView.do")
	public String goAccessView(HttpServletRequest request) {
		return "goAccess/report";
	}
	
	private List<LocalDate> getDatesBetweenTerms(GoAccessDTO goAccessDTO) {

		LocalDate startDate = LocalDate.parse(goAccessDTO.getFromDate());
		LocalDate endDate = LocalDate.parse(goAccessDTO.getToDate());
		
		DateTimeZone SEOUL = DateTimeZone.forID("Asia/Seoul");
		
		DateTime start = new DateTime(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), 0, 0, 0, SEOUL);
		DateTime end = new DateTime(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), 0, 0, 0, SEOUL);
		
		int numOfDays = Days.daysBetween(start.withTimeAtStartOfDay(), end.withTimeAtStartOfDay()).getDays() + 1;
		
		List<LocalDate> daysRange = Stream.iterate(startDate, date -> date.plusDays(1)).limit(numOfDays).collect(Collectors.toList());
		
		/*
		Date startdate = new Date();
		Date enddate = new Date();
		
		List<Date> dates = new ArrayList<Date>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(startdate);

	    while (calendar.getTime().before(enddate))
	    {
	        Date result = calendar.getTime();
	        dates.add(result);
	        calendar.add(Calendar.DATE, 1);
	    }
	    */
	    
	    return daysRange;
	}
}
