package main.src.java.cardoso.SingleByteXOR;

import main.src.java.cardoso.SingleByteXOR.useful.BinUtils;
import main.src.java.cardoso.SingleByteXOR.useful.UserInput;

import javax.lang.model.type.NullType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static main.src.java.cardoso.SingleByteXOR.useful.UserInput.scanner;

public class SingleByteXOR {
    public static void main(String[] args) {

        HashMap pesos = CreateScoreSystem();
//        System.out.println("Insira a string codificada");
//        String hex_big_string = UserInput.scanner();

        String hex_big_string = UserInput.longEncodedString();
        int index = 0;
        HashMap <String, String> provavelmente_em_ingles = new HashMap<String, String>();
        int operacoes = hex_big_string.length()/60;
        for(int i = 0; i < operacoes; i++){
            String split = hex_big_string;
            String hex_string = new String(split.substring((i*60), ((i+1)*60))) ;
            SingleByteXor(provavelmente_em_ingles, hex_string, pesos); //AAAAAAAAAAAAAAAAAAAA
            index++;

        }

        System.out.println("Seguem os provaveis resultados");
        System.out.println(provavelmente_em_ingles);
        System.out.println("Source code(é uma branch do ultimo exercicio): https://github.com/Cardoz-0/SingleByteXOR");
    }


    public static HashMap SingleByteXor(HashMap provavelmente_em_ingles, String hex_string, HashMap pesos){


        String most_used_string = "eEtTaAiInNoOsShHrRdDlLuUcCmMfFwWyYgGpPbBvVkKqQjJxXzZ0123456789+/'";
        char[] most_used_char = most_used_string.toCharArray();
        for (int i= 0; i != most_used_char.length; i++){
            String binary_string = BinUtils.hexToManyBin(hex_string);


            int ascii_code = (int) most_used_char[i];
            String binary_code = BinUtils.intToBin(ascii_code);
            String eightbit_bin = BinUtils.fixBinSize(binary_code, 8);
            String xorred = BinUtils.byteXOR(binary_string, eightbit_bin);

            xorred = BinUtils.binToManyAscii(xorred);

            double valor = EnglishScore(xorred, pesos);
            if ((23 < valor) && (valor < 26)){
                provavelmente_em_ingles.put(xorred, String.valueOf(most_used_char[i]));
            }

        }
        return provavelmente_em_ingles;

    }
    public static double EnglishScore(String phrase, HashMap hash_pesos){
        char[]frase = phrase.toCharArray();
        double soma = 0;

        for(int i=0; i < frase.length; i++){
            String str_double_value = (String) hash_pesos.get(String.valueOf(frase[i]));
            if (str_double_value != null && !str_double_value.isEmpty()){
                double valor = Double.parseDouble(str_double_value);
                soma = soma + valor;
            }

        }
        double media = soma/frase.length;
        return media; //Com base nos valores, a frase media em ingles teria o valor aproximado de 23;
    }


    private static HashMap CreateScoreSystem(){
        //baseado em https://www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html
        HashMap<String, String> hash_peso = new HashMap<String, String>();
        String most_used_string = "eEaArRiIoOtTnNsSlLcCuUdDpPmMhHgGbBfFyYwWkKvVxXzZjJqQ0123456789+/' ";
        char aspas = '"';
        most_used_string = most_used_string + aspas;
        char[] most_used = most_used_string.toCharArray();
        String[] pesos = {"56.88","56.88", "43.31","43.31", "38.64","38.64", "38.45", "38.45", "36.51","36.51", "35.43","35.43", "33.92","33.92", "29.23","29.23", "27.98","27.98", "23.13","23.13", "18.51","18.51",
                "17.25","17.25", "16.14","16.14", "15.36","15.36", "15.31","15.31", "12.59","12.59", "10.56","10.56", "9.24","9.24", "9.06", "9.06","6.57","6.57", "5.61", "5.61","5.13", "5.13","1.48", "1.48","1.39", "1.39", "1.00","1.00", "1.00","1.00",
                "6.8","6.8","6.8","6.8","6.8","6.8","6.8","6.8","6.8","6.8", "0.0", "0.0", "12", "20", "12"};
        //Media aprimaxada e valores após Z são aproximados
        for(int i = 0; i < most_used_string.length(); i++){
            hash_peso.put(String.valueOf(most_used[i]), pesos[i]);
        }
        return hash_peso;
    }
}


