import {
  Autocomplete,
  Button,
  Card,
  CardActions,
  CardContent,
  IconButton,
  TextField,
} from "@mui/material";
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
import { debounce } from "lodash";
import { SingerAllFields } from "../../models/SingerAllFields";
import { Group } from "../../models/Group";

export const AlbumAdd = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  const [idSinger, setIdSinger] = useState<{ idSinger: string }>();
  const [idGroup, setIdGroup] = useState<{ idGroup: string }>();

  const [album, setAlbum] = useState({
    albumName: "",
    yearRelease: 1,
    noSongs: 1,
    idGroup: 1,
    idSinger: 1,
  });

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

  const addAlbum = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      await axios.post(`${BACKEND_API_URL}/albums`, album);
      navigate("/albums");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/singers`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={addAlbum}>
            <TextField
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
              id="yearRelease"
              label="Release year"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAlbum({ ...album, yearRelease: Number(event.target.value) })
              }
            />
            <TextField
              id="noSongs"
              label="Number of songs"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setAlbum({ ...album, noSongs: Number(event.target.value) })
              }
            />

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

            <Button type="submit">Add Singer</Button>
          </form>
        </CardContent>
        <CardActions></CardActions>
      </Card>
    </Container>
  );
};
