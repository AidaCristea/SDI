import {
    TableContainer,
    Paper,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    CircularProgress,
    Container,
    IconButton,
    Tooltip,
    Button,
    Box,
  } from "@mui/material";
  
  import { useEffect, useState } from "react";
  import { Singer } from "../../models/Singer";
  
  import { Link } from "react-router-dom";
  import AddIcon from "@mui/icons-material/Add";
  import ReadMoreIcon from "@mui/icons-material/ReadMore";
  import EditIcon from "@mui/icons-material/Edit";
  import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
  
  import { BACKEND_API_URL } from "../../constants";
import { Album } from "../../models/Album";
  
  export const AlbumShowAll = () => {
    const [loading, setLoading] = useState(false);
    const [albums, setAlbums] = useState<Album[]>([]);
    const [pageSize, setPageSize] = useState(100);
    const [totalAlbums, setTotalAlbums] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
  
    // useEffect(() => {
    //   fetch( `${BACKEND_API_URL}/singers`)
    //     .then((res) => res.json())
    //     .then((data) => {
    //       console.log(data);
    //       setSingers(data);
    //       setLoading(false);
    //     });
    // }, []);
  
    // useEffect(() => {
    //   setLoading(true);
  
    //   const fetchRecLbl = () => {
    //     fetch(`${BACKEND_API_URL}/singers/countAll`)
    //     .then((response) => response.json())
    //     .then((count) => {
    //       fetch(`${BACKEND_API_URL}/singers/page/${currentPage}/size/${pageSize}`)
    //       .then((response) => response.json())
    //       .then((data) => {
    //         setTotalSingers(count);
    //         setSingers(data);
    //         setLoading(false);
    //       });
    //     })
    //     .catch((error) => {
    //       console.error(error);
    //       setLoading(false);
    //     });
    //   };
    //   fetchRecLbl();
    // }, [currentPage, pageSize]);
  
    //  const fetchSingers = async () => {
    //    setLoading(true);
    //    const response = await fetch(
    //      `${BACKEND_API_URL}/singers/page/${currentPage}/size/${pageSize}`
    //    );
    //    const count = await fetch(
    //     `${BACKEND_API_URL}/singers/countAll`
    //   );
  
    //    const {next, previous, results} = await response.json();
    //    const countt = await count.json();
    //    console.log(countt);
    //    console.log(response);
    //    setSingers(results);
    //    setTotalSingers(countt);
    //    setLoading(false);
    //  };
  
    const fetchAlbums = () => {
      fetch(`${BACKEND_API_URL}/albums/countAll`)
        .then((response) => response.json())
        .then((count) => {
          fetch(`${BACKEND_API_URL}/albums/page/${currentPage}/size/${pageSize}`)
            .then((response) => response.json())
            .then((data) => {
              setTotalAlbums(count);
              setAlbums(data);
              setLoading(false);
            });
        })
        .catch((error) => {
          console.error(error);
          setLoading(false);
        });
    };
  
    useEffect(() => {
      fetchAlbums();
    }, [currentPage, pageSize]);
  
    const handlePreviousPage = () => {
      if (currentPage > 0) {
        setCurrentPage(currentPage - 1);
      }
    };
  
    const handleNextPage = () => {
      setCurrentPage(currentPage + 1);
    };
  
    return (
      <Container>
        <h1>All albums</h1>
  
        {loading && <CircularProgress />}
        {!loading && albums.length === 0 && <p>No albums found</p>}
        {!loading && (
          <div style={{ display: "flex", alignItems: "center" }}>
            <IconButton
              component={Link}
              sx={{ marginRight: 0 }}
              to={`/albums/add`}
            >
              <Tooltip title="Add a new album" arrow>
                <AddIcon color="primary" />
              </Tooltip>
            </IconButton>
          </div>
        )}
  
        {!loading && (
          <div style={{ display: "flex", alignItems: "center" }}>
            <Button
              sx={{ color: "black" }}
              disabled={currentPage === 0}
              onClick={handlePreviousPage}
            >
              Previous Page
            </Button>
            <Button
              disabled={albums.length < pageSize}
              sx={{ color: "black" }}
              onClick={handleNextPage}
            >
              Next Page
            </Button>
  
            <Box mx={2} display="flex" alignItems="center">
              Page {currentPage + 1} of {Math.ceil(totalAlbums / pageSize)}
            </Box>
          </div>
        )}
  
        {!loading && albums.length > 0 && (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>#</TableCell>
                  <TableCell align="left">Name</TableCell>
                  <TableCell align="center">Release Year</TableCell>
                  <TableCell align="center">Number of songs</TableCell>
                  <TableCell align="center">Operations</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {albums.map((album: Album, index) => (
                  <TableRow key={album.idAlbum}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      <Link
                        to={`/albums/${album.idAlbum}`}
                        title="View album details"
                      >
                        {album.albumName}
                      </Link>
                    </TableCell>
  
                    <TableCell align="center">{album.yearRelease}</TableCell>
                    <TableCell align="center">{album.noSongs}</TableCell>
                    
                    <TableCell align="right">
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/albums/${album.idAlbum}`}
                      >
                        <Tooltip title="View album details" arrow>
                          <ReadMoreIcon color="primary" />
                        </Tooltip>
                      </IconButton>
  
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/albums/${album.idAlbum}/edit`}
                        title="Edit album details"
                      >
                        <EditIcon />
                      </IconButton>
  
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/albums/${album.idAlbum}/delete`}
                        title="Delete album"
                      >
                        <DeleteForeverIcon sx={{ color: "red" }} />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}
        <Button
          sx={{ color: "black" }}
          disabled={currentPage === 0}
          onClick={handlePreviousPage}
        >
          Previous Page
        </Button>
  
        <Button
          sx={{ color: "black" }}
          disabled={albums.length < pageSize}
          onClick={handleNextPage}
        >
          Next Page
        </Button>
      </Container>
    );
  };
  