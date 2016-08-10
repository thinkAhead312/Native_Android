
<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$users_id =$_POST['users_id'];
		$fName = $_POST['first_name'];
		$lName =  $_POST['sur_name'];	
		$eMail =  $_POST['email'];
		$contactNum = $_POST['contact_num'];
		$bDate = $_POST['birthdate'];
		$gender = $_POST['gender'];
		$photo = $_POST['photo'];
	
		require_once('dbConnect.php');
	
		$sqlVerify ="update users_table set account_verified_flag=1 where users_id='$users_id'";		
		$result = mysqli_query($connection,$sqlVerify);
	
		
		
		$time = strtotime($bDate);
		$bDate = date('y-m-d',$time);
		$gender = (int)$gender;
		
		$path = "uploads/$users_id.png";
		
		$actualpath = "http://192.168.254.181:8081/Change12/Change12_manual/mysql_QUERY/$path";
		$sqlDisciple = "update disciples_table set first_name ='$fName', sur_name='$lName', email='$eMail',contact_num='$contactNum', birthdate='$bDate', gender='$gender', photo='$actualpath'  
						where users_id='$users_id' ";
						
		$result = mysqli_query($connection,$sqlDisciple);
	   
		
		
		if ($sqlVerify && $sqlDisciple){
			file_put_contents($path,base64_decode($photo));
			echo json_encode(array("result"=>array(
			'account_verified_flag' => 1,
			'first_name' => $fName,
			 'sur_name' => $lName,
			 'email' => $eMail,
			 'contact_num' => $contactNum,
			 'birthdate' => $bDate,
			 'gender' => $gender,
			 'photo' => $actualpath
			//'Name' => 'Jonathan Lee'
			)
			)
		);
		} else {
		echo json_encode(array('errorMsg'=>'Some errors occured.'));
		}
		
		mysqli_close($connection); 
	}
	
	/*
echo json_encode(array(
			'account_verified_flag' => 1
		));*/

?>
