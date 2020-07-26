<?php 

include 'connection.php';

 $email = $_POST['email'];
 $password = $_POST['password'];

 $sql = "SELECT * FROM data_table WHERE email='$email' && password='$password'";
 
 $result=mysqli_query($con,$sql);
 if(mysqli_num_rows($result)>0){
	 echo json_encode(array('response' => 'success'));
 }else{
	echo json_encode(array('response' => 'error'));
 }
 
?>