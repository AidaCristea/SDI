import { Button, Card, CardActions, CardContent, CircularProgress, Container, IconButton, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

import { BACKEND_API_URL } from "../../constants";




export const GroupUpdate = () => {

	const navigate = useNavigate();
    const { groupId } = useParams();

	const [loading, setLoading] = useState(true)
	const [group, setGroup] = useState({
        members:1,
        dateFormed:"",
        nameGr:"",
        musicSpecialization:"",
        review:"",
        description:"",
    });

    useEffect(() => {
		const fetchGroup = async () => {
			const response = await fetch(`${BACKEND_API_URL}/groups/${groupId}`);
			const group = await response.json();
			setGroup({
                members:group.members,
                dateFormed:group.dateFormed,
                nameGr:group.nameGr,
                musicSpecialization: group.musicSpecialization,
                review:group.review,
                description:group.description,
                
		})
			setLoading(false);
            console.log(group);
		};
		fetchGroup();
	}, [groupId]);

	const updateGroup = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.put(`${BACKEND_API_URL}/groups/${groupId}`, group);
			navigate(`/groups/${groupId}`);
		} catch (error) {
			console.log(error);
		}
	};


	return (
		<Container>

		{loading && <CircularProgress />}

		{!loading && !group && <div>Group not found</div>}

		{!loading && (
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/groups/${groupId}`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={updateGroup}>
						<TextField value={group.members}
							id="members"
							label="Members"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, members: Number(event.target.value) })}
						/>
						<TextField value={group.dateFormed}
							id="dateFormed"
							label="Date Formed"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, dateFormed: event.target.value })}
						/>

                        <TextField value={group.nameGr}
							id="nameGr"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, nameGr: event.target.value })}
						/>

                        <TextField value={group.musicSpecialization}
							id="musicSpecialization"
							label="Music Specialization"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, musicSpecialization: event.target.value })}
						/>

                        <TextField value={group.review}
							id="review"
							label="Review"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setGroup({ ...group, review: event.target.value })}
						/>
                        <TextField value={group.description}
								id="description"
                                label="Description"
                                variant="outlined"
                                fullWidth
                                sx={{ mb: 2 }}
                                onChange={(event) => setGroup({ ...group, description: event.target.value })}
                            />
                    

						<Button type="submit">Update group</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		)
}
		</Container>
	);
};