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

export const SingerShowAll = () => {
  const [loading, setLoading] = useState(false);
  const [singers, setSingers] = useState<Singer[]>([]);
  const [pageSize, setPageSize] = useState(100);
  const [totalRecLbls, setTotalSingers] = useState(0);
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

  const fetchSingers = () => {
    fetch(`${BACKEND_API_URL}/singers/countAll`)
      .then((response) => response.json())
      .then((count) => {
        fetch(`${BACKEND_API_URL}/singers/page/${currentPage}/size/${pageSize}`)
          .then((response) => response.json())
          .then((data) => {
            setTotalSingers(count);
            setSingers(data);
            setLoading(false);
          });
      })
      .catch((error) => {
        console.error(error);
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchSingers();
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
      <h1>All singers</h1>

      {loading && <CircularProgress />}
      {!loading && singers.length === 0 && <p>No singers found</p>}
      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <IconButton
            component={Link}
            sx={{ marginRight: 0 }}
            to={`/singers/add`}
          >
            <Tooltip title="Add a new singer" arrow>
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
            disabled={singers.length < pageSize}
            sx={{ color: "black" }}
            onClick={handleNextPage}
          >
            Next Page
          </Button>

          <Box mx={2} display="flex" alignItems="center">
            Page {currentPage + 1} of {Math.ceil(totalRecLbls / pageSize)}
          </Box>
        </div>
      )}

      {!loading && singers.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="right">First name</TableCell>
                <TableCell align="right">Last name</TableCell>
                <TableCell align="right">Age</TableCell>
                <TableCell align="center">City</TableCell>
                <TableCell align="right">Type of music</TableCell>
                <TableCell align="center">Operations</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {singers.map((singer: Singer, index) => (
                <TableRow key={singer.id}>
                  <TableCell component="th" scope="row">
                    {index + 1}
                  </TableCell>
                  <TableCell component="th" scope="row">
                    <Link
                      to={`/singers/${singer.id}`}
                      title="View singer details"
                    >
                      {singer.firstName}
                    </Link>
                  </TableCell>

                  <TableCell align="right">{singer.lastName}</TableCell>
                  <TableCell align="right">{singer.age}</TableCell>
                  <TableCell align="right">{singer.city}</TableCell>
                  <TableCell align="right">{singer.typeOfMusic}</TableCell>

                  <TableCell align="right">
                    <IconButton
                      component={Link}
                      sx={{ mr: 3 }}
                      to={`/singers/${singer.id}`}
                    >
                      <Tooltip title="View singer details" arrow>
                        <ReadMoreIcon color="primary" />
                      </Tooltip>
                    </IconButton>

                    <IconButton
                      component={Link}
                      sx={{ mr: 3 }}
                      to={`/singers/${singer.id}/edit`}
                      title="Edit singer details"
                    >
                      <EditIcon />
                    </IconButton>

                    <IconButton
                      component={Link}
                      sx={{ mr: 3 }}
                      to={`/singers/${singer.id}/delete`}
                      title="Delete singer"
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
        disabled={singers.length < pageSize}
        onClick={handleNextPage}
      >
        Next Page
      </Button>
    </Container>
  );
};
