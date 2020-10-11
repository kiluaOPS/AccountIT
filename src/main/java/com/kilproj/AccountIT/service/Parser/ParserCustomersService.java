package com.kilproj.AccountIT.service.Parser;

import com.kilproj.AccountIT.domain.Customer;
import com.kilproj.AccountIT.exception.DateException;
import com.kilproj.AccountIT.service.CustomersService;
import com.kilproj.AccountIT.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ParserCustomersService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CustomersService customersService;

    public LinkedHashMap<Customer, List<Long>> readCustomers(String fileName) {
        LinkedHashMap<Customer, List<Long>> customers = new LinkedHashMap<>();
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader(fileStorageService.
                    loadFileAsResource(fileName).getFile());
            scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] customerInfo = line.split(",");

                String fName = customerInfo[0].replaceAll("\\s+","");
                String lName = customerInfo[1].replaceAll("\\s+","");
                LocalDate dob = parseDate(customerInfo[2].replaceAll("\\s+",""));
                String address = customerInfo[3].replaceAll("\\s+","");
                String email = customerInfo[4].replaceAll("\\s+","");
                String refType = customerInfo[5].replaceAll("\\s+","");
                List<Long> relativesInfo = new ArrayList<>();
                if (customerInfo.length > 6 ) {
                    String[] relatives = customerInfo[6].replaceAll("\\s+","").split("-");
                    for (String r : relatives) {
                        relativesInfo.add(Long.parseLong(r));
                    }
                }
                if (!customersService.existByInfo(fName,lName)) {
                    customers.put(new Customer(fName, lName, dob, address, email, refType), relativesInfo);
                } else {
                    System.out.println("CUSTOMER ALREADY EXIST IN THE DATABASE");
                    System.out.println(fName + " " + lName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (DateException e1) {
            e1.printStackTrace();
        } finally {
            try {
                fileReader.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return customers;
    }

    private LocalDate parseDate(String dateString) throws DateException {
        SimpleDateFormat[] formats = new SimpleDateFormat[]
                        {new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("d-MMM-yy"),
                        new SimpleDateFormat("d-MMM-yyyy"), new SimpleDateFormat("dd-MMM-yyyy"),
                        new SimpleDateFormat("dd/MM/yyyy")};

        Date parsedDate = null;

        for (int i = 0; i < formats.length; i++) {
            try {
                parsedDate = formats[i].parse(dateString);
                return parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                continue;
            }
        }
        throw new DateException("Unknown date format: '" + dateString + "'");
    }
}

