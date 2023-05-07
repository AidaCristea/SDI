import { useEffect, useState } from "react";

import { Singer } from "../../models/Singer";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";

import { BACKEND_API_URL } from "../../constants";
import { SingerAllFields } from "../../models/SingerAllFields";
import { Group } from "../../models/Group";

export const GroupDetails = () => {
    //const { singerId } = useParams();
    const { groupId } = useParams<{ groupId: string }>();
    //const singerIdd = parseInt(singerId ?? '0', 10);
	const [group, setGroup] = useState<Group>();

	useEffect(() => {
		const fetchGroup = async () => {
			// TODO: use axios instead of fetch
			// TODO: handle errors
			// TODO: handle loading state
			const response = await fetch(`${BACKEND_API_URL}/groups/${groupId}`);
            //Number(singerId);
            //let singerIdd = Number(singerId);
			const group = await response.json();
			setGroup(group);
            console.log(group);
		};
		fetchGroup();
	}, [groupId]);
   


    return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/groups`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Group Details</h1>
					<p>Name: {group?.nameGr}</p>
					<p>Members: {group?.members}</p>
					<p>Date Formed: {group?.dateFormed}</p>
                    <p>Music Specialization: {group?.musicSpecialization}</p>
					<p>Review: {group?.review}</p>
					<p>Description: {group?.description}</p>
					
					
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/groups/${groupId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/groups/${groupId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);

}