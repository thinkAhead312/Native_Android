package com.example.glenda_pc.change12_activity.Model;

/**
 * Created by Glenda-PC on 12/15/2015.
 */
public class Disciple_Set_Get {
    private  String users_id, parents_id,
            prime_flag,close_cell_flag,
            consolidates_flag, active,account_verified_flag,
            first_name, sur_name,
            photo, contact_num,
            password,email,birthdate, gender,
            church_testimony, cell_testimony,
            devotional_testimony, encounter_testimony, address, photoFlag ,pepsol_stat, change12_stat, remarks, graduate_flag="0", affliation, time_stamp="", dateUpdated="";

    //Getters and Setters

    public String getTime_stamp(){return  time_stamp;}
    public void setTime_stamp(String time_stamp){this.time_stamp=time_stamp;}

    public  String getAffliation(){return  affliation;}
    public void setAffliation(String affliation){this.affliation = affliation;}

    public String getChange12_stat(){ return change12_stat;}
    public void setChange12_stat(String change12_stat){this.change12_stat=change12_stat;}

    public String getRemarks(){return  remarks;}
    public void setRemarks(String remarks){this.remarks=remarks;}

    public String getGraduate_flag(){return  graduate_flag;}
    public void setGraduate_flag(String graduate_flag){this.graduate_flag=graduate_flag;}

    public String getPepsol_stat(){return  pepsol_stat;}
    public void setPepsol_stat(String pepsol_stat){this.pepsol_stat=pepsol_stat;}

    public String getUsers_id(){ return users_id;}
    public void  setUsers_id(String users_id){ this.users_id=users_id;}

    public String getParents_id(){ return parents_id;}
    public void  setParents_id(String parents_id){ this.parents_id=parents_id;}

    public String getPrime_flag(){ return prime_flag;}
    public void  setPrime_flag(String prime_flag){ this.prime_flag=prime_flag;}

    public String getClose_cell_flag(){ return close_cell_flag;}
    public void  setClose_cell_flag(String close_cell_flag){ this.close_cell_flag=close_cell_flag;}

    public String getConsolidates_flag(){ return consolidates_flag;}
    public void  setConsolidates_flag(String consolidates_flag){ this.consolidates_flag=consolidates_flag;}

    public String getActive(){ return active;}
    public void  setActive(String active){ this.active=active;}

    public String getAccount_verified_flag(){ return account_verified_flag;}
    public void  setAccount_verified_flag(String account_verified_flag){ this.account_verified_flag=account_verified_flag;}

    public String getPhotoFlag(){ return photoFlag;}
    public void  setPhotoFlag(String photoFlag){ this.photoFlag=photoFlag;}

    public String getFirst_name(){ return first_name;}
    public void  setFirst_name(String first_name){ this.first_name=first_name;}

    public String getSur_name(){ return sur_name;}
    public void  setSur_name(String sur_name){ this.sur_name=sur_name;}

    public String getPhoto(){ return photo;}
    public void  setPhoto(String photo){ this.photo=photo;}

    public String getContact_num(){ return contact_num;}
    public void  setContact_num(String contact_num){ this.contact_num=contact_num;}

    public String getPassword(){ return password;}
    public void  setPassword(String password){ this.password=password;}

    public String getEmail(){ return email;}
    public void  setEmail(String email){ this.email=email;}

    public String getBirthdate(){ return birthdate;}
    public void  setBirthdate(String birthdate){ this.birthdate=birthdate;}

    public String getGender(){ return gender;}
    public void  setGender(String gender){ this.gender=gender;}

    public String getChurch_testimony(){ return church_testimony;}
    public void  setChurch_testimony(String church_testimony){ this.church_testimony=church_testimony;}

    public String getCell_testimony(){ return cell_testimony;}
    public void  setCell_testimony(String cell_testimony){ this.cell_testimony=cell_testimony;}

    public String getDevotional_testimony(){ return devotional_testimony; }
    public void  setDevotional_testimony(String devotional_testimony){ this.devotional_testimony=devotional_testimony;}

    public String getEncounter_testimony(){ return encounter_testimony;}
    public void  setEncounter_testimony(String encounter_testimony){ this.encounter_testimony=encounter_testimony;}

    public String getDateUpdated(){ return dateUpdated;}
    public void  setDateUpdated(String dateUpdated){ this.dateUpdated=dateUpdated;}

    public String getAddress(){ return address;}
    public void  setAddress(String address){ this.address=address;}

    private String imageUrl;
    private String name;


    //Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName_1() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
