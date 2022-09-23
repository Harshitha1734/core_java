package com.training.repos;

import static java.util.stream.Collectors.toList;


import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.utils.ConnectionFactory;
import com.training.entity.PersonalDetails;
import com.training.exceptions.ElementNotFoundException;
import com.training.ifaces.CrudRepository;
import com.training.ifaces.EmployeeDetailsCrudRepository;

public class ManagementRepository implements EmployeeDetailsCrudRepository {
	
	private Connection con;
	
	Collection<PersonalDetails> employeeList;
	public ManagementRepository() {
		super();
		this.con =ConnectionFactory.getMySqlConnection();
		this.employeeList =findAll();
		
	}

	@Override
	public boolean save(PersonalDetails obj) {
		
	   String sql="insert into test.employee_details values(?,?,?,?,?,?,?)";
	   int rowUpdated=0;
	   try {
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,obj.getFirstName());
		pstmt.setString(2,obj.getLastName());
		pstmt.setString(3,obj.getAddress());
		pstmt.setString(4,obj.getEmail());
		pstmt.setLong(5,obj.getPhoneNumber());
		pstmt.setDate(6,Date.valueOf(obj.getDateOfBirth()));
		pstmt.setDate(7,Date.valueOf(obj.getWeddingDate()));
		rowUpdated=pstmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	   this.employeeList =findAll();
	 return rowUpdated==1?true:false;
	}
	@Override
	public boolean updateEmailPhone(String newEmail,String givenEmail, long phoneNumber) throws ElementNotFoundException {
        String sql="update test.employee_details set email=?,phone_number=? where email=?";
        int rowUpdated=0;
        try {
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, newEmail);
			pstmt.setLong(2, phoneNumber);
			pstmt.setString(3, givenEmail);
			rowUpdated=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        this.employeeList =findAll();
		return rowUpdated==1?true:false;
	}

	@Override
	public boolean delete(String firstName) throws ElementNotFoundException {
        String sql="delete from test.employee_details where first_name=?";
        int rowDeleted=0;
		try {
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1,firstName);
			rowDeleted=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.employeeList =findAll();
		return rowDeleted==1?true:false;
	}


	@Override
	public Collection<PersonalDetails> findAll() {
		
		List<PersonalDetails> list=new ArrayList<>();
		String sql="select * from test.employee_details";
		try {
			PreparedStatement pstmt=con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
			  String firstName = rs.getString("first_name");
			  String lastName = rs.getString("last_name");
			  String address = rs.getString("address");
			  String email = rs.getString("email");
			 long phoneNumber = rs.getLong("phone_number");
			 Date dob= rs.getDate("date_of_birth");
			 Date wedDate= rs.getDate("wedding_date");
			 PersonalDetails obj= new PersonalDetails(firstName,lastName,address,email,phoneNumber,dob.toLocalDate(),wedDate.toLocalDate());
			 list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Collection<String> findByFnamePhone() throws ElementNotFoundException {
		Map<String, Long> map=this.employeeList.stream()
	  			  .collect(Collectors.toMap(PersonalDetails::getFirstName,PersonalDetails::getPhoneNumber ));
	  	  Collection<String> list=new ArrayList<>();
	  	  map.forEach((x,y)->list.add("FirstName : "+x+"  PhoneNumber : "+y));
			return list;
	}

	@Override
	public Collection<String> findByDob(LocalDate dob) throws ElementNotFoundException {
		 Map<String, String> map=this.employeeList.stream().filter(e->e.getDateOfBirth().equals(dob))
   			  .collect(Collectors.toMap(PersonalDetails::getFirstName,PersonalDetails::getEmail ));
   	  Collection<String> list=new ArrayList<>();
   	  map.forEach((x,y)->list.add("FirstName : "+x+"  Email : "+y));
		return list;
	}

	@Override
	public Collection<String> findByWed(LocalDate weddingDate) throws ElementNotFoundException {
		Map<String, Long> map=this.employeeList.stream().filter(e->e.getWeddingDate().equals(weddingDate))
  			  .collect(Collectors.toMap(PersonalDetails::getFirstName,PersonalDetails::getPhoneNumber ));
  	  Collection<String> list=new ArrayList<>();
  	  map.forEach((x,y)->list.add("FirstName : "+x+"    PhoneNumber:"+y));
		return list;
	}

	@Override
	public Collection<PersonalDetails> findByFirstName(String firstName) throws ElementNotFoundException {
		Collection<PersonalDetails> list=this.employeeList.stream().filter(e->e.getFirstName().contentEquals(firstName)).collect(toList());
	 
	 		return list;
		
	}

}
