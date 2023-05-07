import { Button, Card, CardActions, CardContent, Container, IconButton, TextField } from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

import { BACKEND_API_URL } from "../../constants";
import { Group } from "../../models/Group";


export const GroupsAdd = () => {

    const navigate = useNavigate();

	const [group, setGroup] = useState({
        members:1,
        dateFormed:"",
        nameGr:"",
        musicSpecialization:"",
        review:"",
        description:"",
	});

	const addGroup = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.post( `${BACKEND_API_URL}/groups`, group);
			navigate("/groups");
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/groups`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addGroup}>
						<TextField
							id="members"
							label="Members"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, members: Number(event.target.value) })}
						/>
						<TextField
							id="dateFormed"
							label="Date Formed"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, dateFormed: event.target.value })}
						/>

                        <TextField
							id="nameGr"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, nameGr: event.target.value })}
						/>

                        <TextField
							id="musicSpecialization"
							label="Music Specialization"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, musicSpecialization: event.target.value })}
						/>              

                        <TextField
							id="review"
							label="Review"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, review: event.target.value })}
						/>

                        <TextField
							id="description"
							label="Description"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, description: event.target.value })}
						/>

                       

						<Button type="submit">Add Group</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};