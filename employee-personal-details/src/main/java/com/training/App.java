package com.training;

import java.time.LocalDate;
import java.util.Scanner;

import com.training.entity.PersonalDetails;
import com.training.exceptions.ElementNotFoundException;
import com.training.services.ManagementService;

public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ManagementService service = new ManagementService();
		String firstName;
		String lastName;
		String address;
		String email;
		long phoneNumber;
		String dob;
		String wedDate;
		
		
		
		while(true) {
			System.out.println("1:Add Employee into Database" + "\n" + "2:Find Employees by FirstName" + "\n"
					+ "3:Find Employees by FirstName and PhoneNumber" + "\n" + "4:Update PhoneNumber and Email " + "\n"
					+ "5:Delete Details of Employee " + "\n" + "6:Find Employees by FirstName and Email with matching Date of Birth" + "\n"
					+ "7:Find Employees by FirstName and PhoneNumber with matching  Wedding Date"+"\n"+"8:Exit from Menu");
		System.out.println("Enter Query Number");
		int key= scanner.nextInt();
		switch (key) {
		case 1:
			System.out.println("First Name:");
			firstName = scanner.next();
			System.out.println("Last Name:");
			lastName = scanner.next();
			System.out.println("Address:");
			address = scanner.next();
			System.out.println("Email:");
			email = scanner.next();
			System.out.println("Phone Number:");
			phoneNumber = scanner.nextLong();
			System.out.println("Date of Birth(YYYY-MM-DD):");
			dob = scanner.next();
			System.out.println("Wedding Date(YYYY-MM-DD):");
			wedDate = scanner.next();
			PersonalDetails obj = new PersonalDetails(firstName, lastName, address, email, phoneNumber,
					LocalDate.parse(dob), LocalDate.parse(wedDate));
			System.out.println(service.add(obj));

			break;
		case 2:
			System.out.println("First Name:");
			firstName =scanner.next();
			try {
				service.findByFirstName(firstName);
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				service.findByFirstNamePhone();
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("Email Id:");
			String emailId =scanner.next();
			System.out.println("New Email Id:");
			email = scanner.next();
			System.out.println("Phone Number:");
			phoneNumber = scanner.nextLong();
			try {
				service.updateEmployee(email, emailId, phoneNumber);
			} catch (ElementNotFoundException e1) {
				e1.printStackTrace();
			}
			break;
		case 5:
			System.out.println("First Name:");
			firstName =scanner.next();
			try {
				service.deleteEmployee(firstName);
			} catch (ElementNotFoundException e1) {
				e1.printStackTrace();
			}
			break;
		case 6:
			System.out.println("Date of Birth(YYYY-MM-DD):");
			dob = scanner.next();
			try {
				service.findByDateOfBirth(LocalDate.parse(dob));
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 7:
			System.out.println("Wedding Date(YYYY-MM-DD):");
			wedDate = scanner.next();
			try {
			 service.findByWedDate(LocalDate.parse(wedDate));
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 8:
			 System.out.println("Exit");
			 scanner.close();
			 System.exit(0);
		default:
			break;
		}
	}
}
}