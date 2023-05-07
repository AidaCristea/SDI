import { Autocomplete, Button, Card, CardActions, CardContent, IconButton, TextField } from "@mui/material";
import { Container } from "@mui/system";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
//import { Singer } from "../../models/Singer";

import { BACKEND_API_URL } from "../../constants";
import { RecordLable } from "../../models/RecordLable";
import { Singer } from "../../models/Singer";
import {debounce} from "lodash";
import { SingerAllFields } from "../../models/SingerAllFields";

export const SingerAdd = () => {
	const navigate = useNavigate();
    const { id } = useParams<{ id: string }>();
	
	const [idRec, setIdRec] = useState<{idRec: string}>();

	const [singer, setSinger] = useState({
        firstName: "",
        lastName: "",
        age:1,
        city:"",
        typeOfMusic:"",
		recLblId:1,
	});

	const [recordLbls, setRecordLbls] = useState<RecordLable[]>([]);

	const fetchSuggestions = async(query: string) => {
		try{
			let url = `${BACKEND_API_URL}/recordLbls/autocomplete?query=${query}`;

			const response = await fetch(url);
			
			const data = await response.json();
			setRecordLbls(data);
			console.log(data);
		}
		catch(error)
		{
			console.log("Error fetching suggestions:", error);
		}

	};

	const debouncedFetchSuggestions = useCallback(debounce(fetchSuggestions, 500), []);

	useEffect( () => {
		return () => {
			debouncedFetchSuggestions.cancel();
		};

	}, [debouncedFetchSuggestions]);



	const addSinger = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			//await axios.post(`${BACKEND_API_URL}/recordLbls/${idRec}/singer`, singer);
			await axios.post(`${BACKEND_API_URL}/singers`, singer);
			navigate("/singers");
		} catch (error) {
			console.log(error);
		}
	};

    const handleInputChange = (event: any, value: any, reason:any) => {
		console.log("input", value, reason);

		if(reason=="input")
		{
			debouncedFetchSuggestions(value);
		}
	};


	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/singers`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addSinger}>
						<TextField
							id="firstName"
							label="First Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, firstName: event.target.value })}
						/>
						<TextField
							id="lastName"
							label="Last Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, lastName: event.target.value })}
						/>
                        <TextField
							id="age"
							label="Age"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, age: Number(event.target.value) })}
						/>
                        <TextField
							id="city"
							label="City"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, city: event.target.value })}
						/>
                        <TextField
							id="typeOfMusic"
							label="Type of music"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, typeOfMusic: event.target.value })}
						/>
						{/*<TextField
							id="recLblId"
							label="Record Lable"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer,recLblId:parseInt( event.target.value) })}
							
						/> */}
						<Autocomplete
							id="recLblId"
							options={recordLbls}
							getOptionLabel={(option) => `${option.nameRl}`}
							renderInput={(params) => <TextField {...params} label="RecordLable" variant="outlined" />}
							filterOptions={(options) => options.filter((option) => option.nameRl.toLocaleLowerCase())}
							onInputChange={handleInputChange}
							onChange={(event, value) => {
								if(value)
								{
									console.log(value);
									console.log(value.idRecLbl);
									setSinger({ ...singer, recLblId:value.idRecLbl});
								}
							}}
							/>

                       
						<Button type="submit">Add Singer</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};