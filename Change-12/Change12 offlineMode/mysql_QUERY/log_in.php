
<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		
		
		require_once('dbConnect.php');
	
		/*$sql = "SELECT * 
				FROM disciples_table 
				where email='$email' and password='$password' ";
				*/
		$sql ="SELECT *
			   FROM users_table
			   INNER JOIN disciples_table ON users_table.users_id = disciples_table.users_id
			   WHERE email='$email' and password='$password'	
			 ";
		
		
		
		$result = mysqli_query($connection,$sql);
	
		$items = array();
		if($row=mysqli_fetch_array($result)){
			
			array_push($items,
			array('users_id'=>$row["users_id"],
			'parents_id'=>$row["parents_id"],
			'prime_flag'=>$row["prime_flag"],
			'close_cell_flag'=>$row["close_cell_flag"],
			'consolidates_flag'=>$row["consolidates_flag"],
			'active'=>$row["active"],
			'account_verified_flag'=>$row["account_verified_flag"],
			'first_name'=>$row["first_name"],
			'sur_name'=>utf8_encode($row["sur_name"]),
			'photo'=>$row["photo"],
			'contact_num'=>$row["contact_num"],
			'password'=>$row["password"],
			'email'=>$row["email"],
			'birthdate'=>$row["birthdate"],
			'gender'=>$row["gender"],
			'church_testimony'=>$row["church_testimony"],
			'cell_testimony'=>$row["church_testimony"],
			'devotional_testimony'=>$row["church_testimony"],
			'encounter_testimony'=>$row["church_testimony"],
			'address'=>$row["address"]
			
			));
	
			
		}
		
		

		
		echo json_encode(array("result"=>$items));
	
		mysqli_close($connection); 
	

	}

?>