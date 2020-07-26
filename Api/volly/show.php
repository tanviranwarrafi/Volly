<?php

include 'connection.php';

$sql = "SELECT * FROM data_table";

$res = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($res)){
		array_push($result,array(
				"id"=>$row["id"],
				"name"=>$row["name"],
				"description"=>$row["description"],
				"email"=>$row["email"],
				"password"=>$row["password"]
			)
		);
	}
	echo json_encode(array("result"=>$result));

?>