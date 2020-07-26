<?php 

 include 'connection.php';
 
 $email = $_POST['email'];

 $sql="DELETE FROM data_table where email ='$email'";
 
 if(mysqli_query($con,$sql)){
	echo json_encode(array('response' => 'success'));
 }else{
	echo json_encode(array('response' => 'error'));
 }


?>