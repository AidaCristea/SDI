import { useEffect, useState } from "react";

import { Singer } from "../../models/Singer";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";

import { BACKEND_API_URL } from "../../constants";
import { SingerAllFields } from "../../models/SingerAllFields";
import { AlbumAllFields } from "../../models/AlbumAllFields";

export const AlbumDetails = () => {
    //const { singerId } = useParams();
    const { albumId } = useParams<{ albumId: string }>();
    //const singerIdd = parseInt(singerId ?? '0', 10);
	const [album, setAlbum] = useState<AlbumAllFields>();

	useEffect(() => {
		const fetchAlbum = async () => {
			// TODO: use axios instead of fetch
			// TODO: handle errors
			// TODO: handle loading state
			const response = await fetch(`${BACKEND_API_URL}/albums/${albumId}`);
            //Number(singerId);
            //let singerIdd = Number(singerId);
			const album = await response.json();
			setAlbum(album);
            console.log(album);
		};
		fetchAlbum();
	}, [albumId]);
   


    return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Album Details</h1>
					<p>Name: {album?.albumName}</p>
					<p>Release year: {album?.yearRelease}</p>
					<p>Number of Songs: {album?.noSongs}</p>
                    <p>Group name: {album?.group.nameGr}</p>
                    <p>Singer first name: {album?.singer.firstName}</p>
                    <p>Singer last name: {album?.singer.lastName}</p>

					
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums/${albumId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/albums/${albumId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);

}