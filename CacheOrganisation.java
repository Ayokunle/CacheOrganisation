/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cache.organisation;

import java.util.ArrayList;

/**
 *
 * @author ayokunle
 */
public class CacheOrganisation {
    
    static int L, N, K;
    static String [][] tags;
    static String [][] data;
    
    CacheOrganisation(int L, int N, int K){
        this.K = K;
        this.L = L;
        this.N = N;
        tags = new String[N][K];
        data = new String[N][(L/4)*K];
        
        for(int i = 0; i< N; i++){
            for(int j = 0; j < K; j++){
                tags[i][j] = Integer.toString(j);
                System.out.print( tags[i][j] +" ");
            }
            System.out.println();
        }
        System.out.println();
        
        for(int i = 0; i< data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                data[i][j] = Integer.toString(j);
                System.out.print( data[i][j] +" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public String convertHexToString(String hex){
	  String str = Integer.toBinaryString(Integer.parseInt(hex, 16)); 
          if(str.length() >= 16){
              return str;
          }else{
                String zero_pad = "0";
                for(int i=1; i< 17-str.length();i++){ 
                    zero_pad = zero_pad + "0"; 
                }
                return zero_pad + str;
          }
    }
    
    static public int convertDecToInt(int num){
        String x = Integer.toBinaryString(num);
        return x.length();
    }
    
    public int getSet(String binary_number, int x){
        String set = binary_number.substring(x, 13);
        return Integer.parseInt(set, 2);
    }
    
    public String getTag(String binary_number, int x){
        return binary_number.substring(1, x);
    }
    
    public void Shift_AddTag(String tag, int set){
        System.out.println("Shifting tag... ");
        for(int k = tags[set].length;  k > 0; k--){
            try{
                tags[set][k] = tags[set][k-1];
            }catch(Exception e){
                //
            }
        }
        tags[set][0] = tag;
        printTag(set);
    }
    
    public void printTag(int set){
        System.out.println("Printing tag... ");
        for(int k =0; k < tags[set].length; k++){
            System.out.print(tags[set][k]+ "-");
        }
        System.out.println();
        System.out.println();
        printSets();
    }
    
     public void Shift_AddData(String binary, int set){
        System.out.println("Shifting data... ");
        for(int k = data[set].length;  k > 0; k--){
            try{
                data[set][k] = data[set][k-1];
            }catch(Exception e){
                //
            }
        }
        data[set][0] = binary;
        printData(set);
    }
    
     public void printData(int set){
        System.out.println("Printing data... ");
        for(int k =0; k < data[set].length; k++){
            System.out.print(data[set][k]+ "-");
        }
        System.out.println();
        System.out.println();
    }
     
     public void printSets(){
         System.out.println("Sets:");
         for(int i = 0; i< N; i++){
            for(int j = 0; j < K; j++){
                System.out.print(tags[i][j] +" ");
            }
            System.out.println();
        }
        System.out.println();
     }
     
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        // TODO code application logic here
        CacheOrganisation x = new CacheOrganisation(16,1,8);//L N K
        
        String [] addresses = {"0000", "0004", "000c", "2200",
                               "00d0", "00e0", "1130", "0028",
                               "113c", "2204", "0010", "0020",
                               "0004", "0040", "2208", "0008",
                               "00a0", "0004", "1104", "0028",
                               "000c", "0084", "000c", "3390", 
                               "00b0", "1100", "0028", "0064" ,
                               "0070", "00d0", "0008", "3394" };
        
        ArrayList<String> seen_addresses = new ArrayList<String>();
        
        int size_of_set = convertDecToInt(N);
        System.out.println("size_of_set - " +size_of_set);
        int set_index = 0;
        set_index = (13 - size_of_set)+1;
        System.out.println("set index - " +set_index);
        int hit =0;
        int miss = 0;
        
        int compulsory_miss=0;
        
        for(int i =0; i< addresses.length; i++){
            System.out.println(addresses[i]);
            String binary = x.convertHexToString(addresses[i]);
            
            System.out.println("Binary - " +binary);
            int set = 0;
            if(N == 1){
                set = 0;
                System.out.println("Set - "+set);
            }else{
                set = x.getSet(binary, set_index);
                System.out.println("Set - "+set);
            }
            String tag = x.getTag(binary, set_index);
            System.out.println("Tag - "+tag);
            //System.out.println("tags[set].length " + tags[set].length);
            
            boolean contains = false;
            for(String curVal : seen_addresses){
                if(curVal.contains(Integer.toString(set))){
                    contains = true;
                }
            }
            seen_addresses.add(Integer.toString(set));
            
            boolean found_tag = false;
            boolean found_data = false;
            
            for(int j = 0; j < tags[set].length;){
                
                if(tags[set][j].equals(tag)){
                    System.out.println( tag + " = " + tags[set][j]);
                    //check data array
                    found_tag = true;
                    hit++;
                    System.out.println("Hit - "+hit);
                    
                    for(int k = 0; k <data[set].length; k++){
                        if(data[set][k].equals(addresses[i])){
                            //
                            System.out.println(addresses[i] + " is found in data.");
                            found_data = true;
                            System.out.println();
                        }
                    }//if tag is found -> if data is found
                    
                    if(found_data == false){
                        System.out.println(addresses[i] +" data not found");
                        x.Shift_AddData(addresses[i], set);
                    }//if data is not found
                }
                j++;
            }//tag search
            
            if(found_tag == false){
                System.out.println(tag +" tag not found");
                miss++;
                System.out.println("Miss - "+miss);
                if(contains == false){
                    compulsory_miss++;
                    System.out.println("Compulsory miss - "+compulsory_miss);
                }
                // shift right, add new tag, add new data
                x.Shift_AddTag(tag, set);
                x.Shift_AddData(addresses[i], set);
            }
            found_tag = false;
        }//addresses array
        
        x.printSets();
        
        for(int i = 0; i< data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                System.out.print( data[i][j] +" ");
            }
            System.out.println();
        }
        System.out.println();
        
        
        System.out.println("Miss " + miss);
        System.out.println("Compulsory miss - "+compulsory_miss);
        System.out.println("Hits " + hit);
    }
}

