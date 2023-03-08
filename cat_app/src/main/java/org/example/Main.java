package org.example;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws IOException {
        int menu_option = -1;
        String[] buttons = {"1. Show cats","2.Show Favourites", "3. Exit"};

        do {
            //Menú principal
            String option = (String) JOptionPane.showInputDialog(null, "Java Cats" , "Main Menu" , JOptionPane.INFORMATION_MESSAGE, null, buttons , buttons[0]);


            //Validamos que opcion selecciona el usuario
            menu_option = Arrays.asList(buttons).indexOf(option);

            switch (menu_option) {
                case 0:
                    CatService.showCats();
                    break;
                case 1:
                    Cat cat = new Cat();
                    CatService.showFavouriteCats(cat.getApikey());
                case 3:
                    break;
                default:
                    break;
            }
        }while (menu_option != 1);

    }
}