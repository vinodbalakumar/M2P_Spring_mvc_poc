package com.devcases.usermanager.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.devcases.usermanager.model.User;
import com.devcases.usermanager.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		model.addAttribute("users", userService.getAllUsers());

		return "home";
	}

	@RequestMapping(value = "/uploadexcel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView addUser(@RequestParam MultipartFile file) throws IOException {
		String uploadExcel = this.uploadExcel(file);
		return new ModelAndView("home", "message", uploadExcel);
	}

	@RequestMapping(value = "/userview", method = RequestMethod.GET)
	public String userList(Model model) {

		model.addAttribute("users", userService.getAllUsers());

		return "userView";
	}

	public String uploadExcel(MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		if (!originalFilename.contains("xlsx") && !originalFilename.contains("xls")) {
			return "Invalid Excel File";
		}
		Workbook excellFile = new XSSFWorkbook(file.getInputStream());
		if (excellFile.getNumberOfSheets() > 0) {
			Sheet oThSheet = excellFile.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			List<User> userList = new ArrayList<>();
			for (int i = 1; i < oThSheet.getPhysicalNumberOfRows(); i++) {
				User user = new User();
				try {
					// sno
					if (oThSheet.getRow(i) != null && oThSheet.getRow(i).getCell(0) != null
							&& !dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(0)).isEmpty()) {
						String sno = dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(0));
						System.out.println(sno);
						// user.setSno(Integer.parseInt(sno));
					}
					// name
					if (oThSheet.getRow(i) != null && oThSheet.getRow(i).getCell(1) != null) {
						String name = dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(1));
						if (name == null || name.length() < 3 || name.length() > 25
								|| name.chars().allMatch(Character::isDigit)) {
							System.out.println("Not valid name");
							continue;
						}
						user.setName(this.maintainSingleSpace(name));
					} else {
						continue;
					}

					// dob
					if (oThSheet.getRow(i) != null && oThSheet.getRow(i).getCell(2) != null
							&& !dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(2)).isEmpty()) {
						String dob = dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(2));
						try {
							LocalDate parsedDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
							if (parsedDate.getYear() >= 1901) {
								user.setDob(parsedDate);
							}
						} catch (Exception e) {
							System.out.println("Not valid date");
						}
					}
					// mobile
					if (oThSheet.getRow(i) != null && oThSheet.getRow(i).getCell(3) != null
							&& !dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(3)).isEmpty()) {
						String phoneNumber = dataFormatter.formatCellValue(oThSheet.getRow(i).getCell(3));
						if (phoneNumber == null || phoneNumber.length() < 10
								|| !phoneNumber.chars().allMatch(Character::isDigit))
							System.out.println("Not valid number");
						else
							user.setMobile(new String(Base64.getEncoder().encode(phoneNumber.getBytes())));

					}

					userList.add(user);
					System.out.println(userList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			userList.forEach(usr -> {
				this.userService.save(usr);
			});
		}
		excellFile.close();
		return "Users Uploaded successfully";
	}

	public String maintainSingleSpace(String name) {
		String[] split = name.split("\\s+");
		StringBuilder builder = new StringBuilder();
		for (String val : split) {
			builder.append(val.trim());
			builder.append(" ");
		}
		System.out.println(builder.toString());
		return builder.toString().trim();
	}
}
