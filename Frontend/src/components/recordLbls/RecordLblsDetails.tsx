import { useEffect, useState } from "react";

import { Singer } from "../../models/Singer";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";
import { RecordLable } from "../../models/RecordLable";

import { BACKEND_API_URL } from "../../constants";
import { RecordLblsAllFields } from "../../models/RecordLblsAllFields";


export const RecordLblsDetails = () => {
	//const { id } = useParams<{ id: string }>();

    //const { singerId } = useParams();


	const { recLblId } = useParams<{ recLblId: string }>();
	
	//const [recordLbl, setRecordLbl] = useState<RecordLable>();

	const [recordLbl, setRecordLbl] = useState<RecordLblsAllFields>();

	useEffect(() => {
		const fetchRecordLable = async () => {
			// TODO: use axios instead of fetch
			// TODO: handle errors
			// TODO: handle loading state
			
			const response = await fetch(`${BACKEND_API_URL}/recordLbls/${recLblId}`);
            
			const recordLbl = await response.json();
			setRecordLbl(recordLbl);
            console.log(recordLbl);
		};
		fetchRecordLable();
	}, [recLblId]);
    console.log(recLblId)
   


    return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/recordLbls`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Record lable Details</h1>
					<p>Record lable Name: {recordLbl?.nameRl}</p>
					<p>Record lable Address: {recordLbl?.address}</p>
					<p>Record lable Price: {recordLbl?.price}</p>
                    <p>Record lable Review: {recordLbl?.review}</p>
                    <p>Record lable nr collaborations: {recordLbl?.nrCollaborations}</p>
					
                
					
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/recordLbls/${recLblId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/recordLbls/${recLblId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);

}