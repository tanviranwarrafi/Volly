<?php 

include 'connection.php';

 $name = $_POST['name'];
 $description = $_POST['description'];
 $email = $_POST['email'];
 $password = $_POST['password'];
 
 $sql="UPDATE data_table SET name='$name',description='$description',password='$password' where email ='$email'"; 
 
 if(mysqli_query($con,$sql)){
	echo json_encode(array('response' => 'success'));
 }else{
	echo json_encode(array('response' => 'error'));
 }

?>