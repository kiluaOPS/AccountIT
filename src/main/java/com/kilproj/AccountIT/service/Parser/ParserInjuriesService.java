package com.kilproj.AccountIT.service.Parser;


import com.kilproj.AccountIT.domain.Injury;
import com.kilproj.AccountIT.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class ParserInjuriesService {

    @Autowired
    private FileStorageService fileStorageService;

    public ArrayList<Injury> readInjuries(String fileName) {
        ArrayList<Injury> injuries = new ArrayList<>();
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader(fileStorageService.
                    loadFileAsResource(fileName).getFile());
            scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                //logic for injuries
                String injuryType = scanner.nextLine();
                System.out.println(injuryType);
                injuries.add(new Injury(injuryType));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return injuries;
    }
}
