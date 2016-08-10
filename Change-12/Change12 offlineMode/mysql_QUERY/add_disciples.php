
<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$parent_id =$_POST['parents_id'];  //$_POST['users_id'];  //this id is the parent_id for the disciples users_id. i.e Cell Leader of a Disciple
		$prime_flag=0;
		$close_cell_flag=$_POST['close_cell_flag'];
		$consolidates_flag=0;
		$active = 1;
		$account_verified_flag=0;
		
		$first_name=$_POST['first_name'];
		$sur_name =$_POST['sur_name'];
		$photo=$_POST['photo'];
		$contact_num=$_POST['contact_num'];
		$password=$_POST['password'];
		$email =$_POST['email'];
		$birthdate=$_POST['birthdate'];
		$gender =$_POST['gender'];
		$address=$_POST['address'];
		$church_testimony="";
		$cell_testimony="";
		$devotional_testimony="";
		$encounter_testimony="";
		
		
		$time = strtotime($birthdate);
		$birthdate = date('y-m-d',$time);
		$close_cell_flag=(int)$close_cell_flag;
		$gender = (int)$gender;
		
		require_once('dbConnect.php'); //
		
		$sql ="SELECT users_id FROM users_table"; //Query all user_id from users_table table
			$res = mysqli_query($connection,$sql);//Execute Query
			$itemArray=array();					  //Initiate Array
			while($rows= mysqli_fetch_array($res)){	//Fetch array from the Query Syntax
				$users_id=(int)$rows['users_id'];   
				array_push($itemArray, $users_id); //push to $itemArray the 
			}	
			$n = 1; //Number
			while (in_array($n,$itemArray )) { //while true  
			   $n = rand(1,9999); 
		}
		$users_id=randomID($n);//  call method to randomID
	
		$sqlInsertUsers = "INSERT INTO users_table (users_id,parents_id, prime_flag, close_cell_flag, consolidates_flag, active, account_verified_flag )
				VALUES ('$users_id', '$parent_id','$prime_flag','$close_cell_flag','$consolidates_flag','$active','$account_verified_flag')";

		mysqli_query($connection, $sqlInsertUsers);
	
		
			
			$path = "uploads/$users_id.png";
			$actualpath = "http://192.168.254.181:8081/Change12/Change12_manual/mysql_QUERY/$path";
			
			$sqlInsertDisciples ="INSERT INTO disciples_table(users_id,first_name, sur_name, photo, contact_num, password, email, birthdate, gender, address )
					VALUES ('$users_id', '$first_name','$sur_name','$actualpath','$contact_num','$password', '$email', '$birthdate', '$gender', '$address' )";
			
			 $items = array();
			 if(mysqli_query($connection,$sqlInsertDisciples)){
				 file_put_contents($path,base64_decode($photo));
				 
				 array_push($items,
					array('users_id'=>$users_id,
					'parents_id'=>$parent_id,
					'prime_flag'=>$prime_flag,
					'close_cell_flag'=>$close_cell_flag,
					'consolidates_flag'=>$consolidates_flag,
					'active'=>$active,
					'account_verified_flag'=>$account_verified_flag,
					'first_name'=>$first_name,
					'sur_name'=>utf8_encode($sur_name),
					'photo'=>$actualpath,
					'contact_num'=>$contact_num,
					'password'=>$password,
					'email'=>$email,
					'birthdate'=>$birthdate,
					'gender'=>$gender,
					'church_testimony'=>$church_testimony,
					'cell_testimony'=>$cell_testimony,
					'devotional_testimony'=>$devotional_testimony,
					'encounter_testimony'=>$encounter_testimony,
					'address'=>$address		
				));
		 
			 }else{
			 echo "Could not register";
			 }
			 echo json_encode(array("result"=>$items));
		

		mysqli_close($connection); 		
	}

?>

<?php
	function randomID($random_id){
	
			$ranStr = (string)($random_id); //convert $random_id to String
			$strLenght= strlen($ranStr); //get the lenght of ranStr
			$users_id="";
			$strBuffer="";
			
			if($strLenght==3){            ////////////////////////////////////////////
			   $strBuffer="0". $ranStr;	  ////////////////////////////////////////////
			}else if($strLenght==2) {	  ////////////////////////////////////////////
			   $strBuffer="00". $ranStr;  ///////////
			}							  /////////// Append Strings. 					
			else if($strLenght==1) {      /////////// Ex. $ranStr = 5;
			   $strBuffer="000". $ranStr; /////////// Append = "000" + "5" = 0005
			}                             /////////// Ex. $ranStr = 100;
			else {                        /////////// Append = "0" + "100" = 0100
				$strBuffer =$ranStr;      ////////////////////////////////////////////
			}                             ////////////////////////////////////////////
			
			return $strBuffer;
		}
?>
