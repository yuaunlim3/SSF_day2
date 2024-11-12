package vttp.ssf.day2.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.ssf.day2.model.Constants;
import vttp.ssf.day2.model.numFile;

@Controller
@RequestMapping("/number")
public class numberContoller {

        @GetMapping()
        public String getNum(
                        @RequestParam (required = false)String name,
                        @RequestParam (required = false)String range,
                        @RequestParam(required = false)String list,
                        Model model) {
                int removeRange = 1;
                int removeName = 1;
                numFile[] numbers = new numFile[4];
                int numRange = 4;
                if(name == null||name.isEmpty()){
                        removeName = 0;
                }
                if(range == null||range.isEmpty()){
                        removeRange = 0;
                }
                if (list == null) {
                        try {
                                numRange = Integer.parseInt(range);
                        } catch (Exception ex) {
                        }

                        Random rand = new SecureRandom();
                        numbers = new numFile[numRange];
                        List<Integer> numTaken = new ArrayList<>();
                        for (int i = 0; i < numRange; i++) {
                                numFile number = new numFile();
                                int nextNum = rand.nextInt(31);
                                while (numTaken.contains(nextNum)) {
                                        nextNum = rand.nextInt(31);
                                }
                                numTaken.add(nextNum);
                                number.setNumber(nextNum);
                                number.setFile(String.format("/images/numbers/%s", Constants.NUMBERS[nextNum]));
                                numbers[i] = number;
                        }
                }else{

                        removeRange = 0;
                        String[] numList = list.split(",");
                        for(int i = 0; i < numList.length;i++){
                                numFile number = new numFile();
                                int nextNum = Integer.parseInt(numList[i]);
                                number.setNumber(nextNum);
                                number.setFile(String.format("/images/numbers/%s", Constants.NUMBERS[nextNum]));
                                numbers[i]=number;
                        }
                }

                model.addAttribute("images", numbers);
                model.addAttribute("name", name);
                model.addAttribute("range", range);
                model.addAttribute("removeName", removeName);
                model.addAttribute("removeRange", removeRange);
                return "number";

        }

}
