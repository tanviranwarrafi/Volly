<?php 

include 'connection.php';
 
 if($con){ 
 
	$name = $_POST['name'];
	$description = $_POST['description'];
	$email = $_POST['email'];
	$password = $_POST['password'];
 
	$query = "SELECT * FROM data_table WHERE email='$email'";
 
	$result = mysqli_query($con,$query);
	$row = mysqli_fetch_assoc($result);
 
	if(isset($row['name'])) {
		echo json_encode(array('response' => 'registered'));
	} else {
		$sql = "insert into data_table (name,description,email,password) values('$name','$description','$email','$password')";
		
		if(mysqli_query($con,$sql)){
			echo json_encode(array('response' => 'success'));
		} else {
			echo json_encode(array('response' => 'error'));
		}
	}
	
 } 
 else{
	echo json_encode(array('response' => 'Error Connection'));
}

 mysqli_close($con);

?>