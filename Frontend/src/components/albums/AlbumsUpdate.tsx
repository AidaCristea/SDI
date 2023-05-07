import {
    Autocomplete,
  Button,
  Card,
  CardActions,
  CardContent,
  CircularProgress,
  Container,
  IconButton,
  TextField,
} from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";

import { BACKEND_API_URL } from "../../constants";
import { SingerAllFields } from "../../models/SingerAllFields";
import { Group } from "../../models/Group";
import { debounce } from "lodash";

export const AlbumUpdate = () => {
  const navigate = useNavigate();
  const { albumId } = useParams();

  const [loading, setLoading] = useState(true);
  const [album, setAlbum] = useState({
    albumName: "",
    yearRelease: 1,
    noSongs: 1,
    idGroup: 1,
    idSinger: 1,
  });

  useEffect(() => {
    const fetchAlbum = async () => {
      const response = await fetch(`${BACKEND_API_URL}/albums/${albumId}`);
      const album = await response.json();
      setAlbum({
        albumName: album.albumName,
        yearRelease: album.yearRelease,
        noSongs: album.noSongs,
        idGroup: album.idGroup,
        idSinger: album.idSinger,
      });
      setLoading(false);
      console.log(album);
    };
    fetchAlbum();
  }, [albumId]);

  const updateAlbum = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      await axios.put(`${BACKEND_API_URL}/albums/${albumId}`, album);
      navigate(`/albums/${albumId}`);
    } catch (error) {
      console.log(error);
    }
  };


  const [singers, setSingers] = useState<SingerAllFields[]>([]);
  const [groups, setGroups] = useState<Group[]>([]);

  const fetchSuggestionsGroups = async (query: string) => {
    try {
      let url = `${BACKEND_API_URL}/groups/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();
      setGroups(data);
      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };

  const debouncedFetchSuggestionsGroups = useCallback(
    debounce(fetchSuggestionsGroups, 500),
    []
  );

  useEffect(() => {
    return () => {
      debouncedFetchSuggestionsGroups.cancel();
    };
  }, [debouncedFetchSuggestionsGroups]);

  const handleInputChangeGroups = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchSuggestionsGroups(value);
    }
  };

  const fetchSuggestionsSingers = async (query: string) => {
    try {
      let url = `${BACKEND_API_URL}/singers/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();
      setSingers(data);
      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };

  const debouncedFetchSuggestionsSingers = useCallback(
    debounce(fetchSuggestionsSingers, 500),
    []
  );

  useEffect(() => {
    return () => {
      debouncedFetchSuggestionsSingers.cancel();
    };
  }, [debouncedFetchSuggestionsSingers]);

  const handleInputChangeSingers = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchSuggestionsSingers(value);
    }
  };





  return (
    <Container>
      {loading && <CircularProgress />}

      {!loading && !album && <div>Album not found</div>}

      {!loading && (
        <Card>
          <CardContent>
            <IconButton
              component={Link}
              sx={{ mr: 3 }}
              to={`/albums/${albumId}`}
            >
              <ArrowBackIcon />
            </IconButton>{" "}
            <form onSubmit={updateAlbum}>
              <TextField
                value={album.albumName}
                id="albumName"
                label="Name"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setAlbum({ ...album, albumName: event.target.value })
                }
              />
              <TextField
                value={album.yearRelease}
                id="yearRelease"
                label="Release year"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setAlbum({
                    ...album,
                    yearRelease: Number(event.target.value),
                  })
                }
              />

              <TextField
                value={album.noSongs}
                id="noSongs"
                label="Number of songs"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setAlbum({ ...album, noSongs: Number(event.target.value) })
                }
              />

              {/* <TextField
                value={album.idGroup}
                id="idGroup"
                label="Group"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setAlbum({ ...album, idGroup: Number(event.target.value) })
                }
              />

              <TextField
                value={album.idSinger}
                id="idSinger"
                label="Singer"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setAlbum({ ...album, idSinger: Number(event.target.value) })
                }
              /> */}
              <Autocomplete
              id="idGroup"
              options={groups}
              getOptionLabel={(option) => `${option.nameGr}`}
              renderInput={(params) => (
                <TextField {...params} label="Group" variant="outlined" />
              )}
              filterOptions={(options) =>
                options.filter((option) => option.nameGr.toLocaleLowerCase())
              }
              onInputChange={handleInputChangeGroups}
              onChange={(event, value) => {
                if (value) {
                  console.log(value);
                  console.log(value.idGroup);
                  setAlbum({ ...album, idGroup: value.idGroup });
                }
              }}
            />
            <Autocomplete
              id="idSinger"
              options={singers}
              getOptionLabel={(option) => `${option.firstName}`}
              renderInput={(params) => (
                <TextField {...params} label="Singer" variant="outlined" />
              )}
              filterOptions={(options) =>
                options.filter((option) => option.firstName.toLocaleLowerCase())
              }
              onInputChange={handleInputChangeSingers}
              onChange={(event, value) => {
                if (value) {
                  console.log(value);
                  console.log(value.id);
                  setAlbum({ ...album, idSinger: value.id });
                }
              }}
            />

              <Button type="submit">Update album</Button>
            </form>
          </CardContent>
          <CardActions></CardActions>
        </Card>
      )}
    </Container>
  );
};
