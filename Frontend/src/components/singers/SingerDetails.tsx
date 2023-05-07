import { useEffect, useState } from "react";

import { Singer } from "../../models/Singer";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";

import { BACKEND_API_URL } from "../../constants";
import { SingerAllFields } from "../../models/SingerAllFields";

export const SingerDetails = () => {
    //const { singerId } = useParams();
    const { singerId } = useParams<{ singerId: string }>();
    //const singerIdd = parseInt(singerId ?? '0', 10);
	const [singer, setSinger] = useState<SingerAllFields>();

	useEffect(() => {
		const fetchSinger = async () => {
			// TODO: use axios instead of fetch
			// TODO: handle errors
			// TODO: handle loading state
			const response = await fetch(`${BACKEND_API_URL}/singers/${singerId}`);
            //Number(singerId);
            //let singerIdd = Number(singerId);
			const singer = await response.json();
			setSinger(singer);
            console.log(singer);
		};
		fetchSinger();
	}, [singerId]);
   


    return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/singers`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Singer Details</h1>
					<p>First Name: {singer?.firstName}</p>
					<p>Last Name: {singer?.lastName}</p>
					<p>Age: {singer?.age}</p>
                    <p>Type of Music: {singer?.typeOfMusic}</p>
					<p>RecordLable: {singer?.recLbl.nameRl}</p>
					<p>RecordLable Adress: {singer?.recLbl.address}</p>
					<p>RecordLable Nr Collaborations: {singer?.recLbl.nrCollaborations}</p>
					<p>RecordLable Price: {singer?.recLbl.price}</p>
					<p>RecordLable Review: {singer?.recLbl.review}</p>
					
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/singers/${singerId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/singers/${singerId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);

}