import { Autocomplete, Button, Card, CardActions, CardContent, CircularProgress, Container, IconButton, TextField } from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

import { BACKEND_API_URL } from "../../constants";
import { RecordLable } from "../../models/RecordLable";
import {debounce} from "lodash";



export const SingerUpdate = () => {

	const navigate = useNavigate();
    const { singerId } = useParams();

	const [loading, setLoading] = useState(true)
	const [singer, setSinger] = useState({
        firstName: "",
        lastName: "",
        age:1,
        city:"",
        typeOfMusic:"",
		recLblId:1
    });

    useEffect(() => {
		const fetchSinger = async () => {
			const response = await fetch(`${BACKEND_API_URL}/singers/${singerId}`);
			const singer = await response.json();
			setSinger({
                firstName:singer.firstName,
                lastName:singer.lastName,
                age:singer.age,
                city:singer.city,
                typeOfMusic:singer.typeOfMusic,
                recLblId:singer.recLblId,
		})
			setLoading(false);
            console.log(singer);
		};
		fetchSinger();
	}, [singerId]);




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

	const handleInputChange = (event: any, value: any, reason:any) => {
		console.log("input", value, reason);

		if(reason=="input")
		{
			debouncedFetchSuggestions(value);
		}
	};


	const updateSinger = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.put(`${BACKEND_API_URL}/singers/${singerId}`, singer);
			navigate(`/singers/${singerId}`);
		} catch (error) {
			console.log(error);
		}
	};


	return (
		<Container>

		{loading && <CircularProgress />}

		{!loading && !singer && <div>Singer not found</div>}

		{!loading && (
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/singers/${singerId}`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={updateSinger}>
						<TextField value={singer.firstName}
							id="firstName"
							label="First Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, firstName: event.target.value })}
						/>
						<TextField value={singer.lastName}
							id="lastName"
							label="Last Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, lastName: event.target.value })}
						/>

                        <TextField value={singer.age}
							id="age"
							label="Age"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, age: Number(event.target.value) })}
						/>

                        <TextField value={singer.city}
							id="city"
							label="City"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, city: event.target.value })}
						/>

                        <TextField value={singer.typeOfMusic}
							id="typeOfMusic"
							label="Type of music"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, typeOfMusic: event.target.value })}
						/>
                        {/* <TextField value={singer.recLblId}
							id="recLblId"
							label="Record Lable"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setSinger({ ...singer, recLblId:Number( event.target.value) })}
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
                    

						<Button type="submit">Update singer</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		)
}
		</Container>
	);
};