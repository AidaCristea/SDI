import { AppBar, Box, Button, IconButton, Toolbar, Typography } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import LocalLibraryIcon from "@mui/icons-material/LocalLibrary";
import SchoolIcon from "@mui/icons-material/School";
import LibraryMusicIcon from '@mui/icons-material/LibraryMusic';
import InterpreterModeIcon from '@mui/icons-material/InterpreterMode';
import EmergencyRecordingIcon from '@mui/icons-material/EmergencyRecording';
import StarBorderPurple500Icon from '@mui/icons-material/StarBorderPurple500';
import GroupsIcon from '@mui/icons-material/Groups';
import CollectionsBookmarkIcon from '@mui/icons-material/CollectionsBookmark';

export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;

	return (
		<Box sx={{ flexGrow: 1 }}>
			<AppBar position="fixed" sx={{ marginBottom: "20px", backgroundColor: "#813da1"}}>
				<Toolbar>
					<IconButton
						component={Link}
						to="/"
						size="large"
						edge="start"
						color="inherit"
						aria-label="school"
						sx={{ mr: 2 }}>
						<LibraryMusicIcon />
					</IconButton>
					<Typography variant="h6" component="div" sx={{ mr: 5 }}>
						Singers management
					</Typography>
					<Button
						variant={path.startsWith("/singers") ? "outlined" : "text"}
						to="/singers"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<InterpreterModeIcon />}>
						Singers
					</Button>
					<Button
						variant={path.startsWith("/recordLbls") ? "outlined" : "text"}
						to="/recordLbls"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<EmergencyRecordingIcon />}>
						Record Lables
					</Button>
					<Button
						variant={path.startsWith("/average-age") ? "outlined" : "text"}
						to="/average-age"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<StarBorderPurple500Icon />}>
						Record Lables Ordered
					</Button>
					<Button
						variant={path.startsWith("/groups") ? "outlined" : "text"}
						to="/groups"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<GroupsIcon />}>
						Groups
					</Button>
					<Button
						variant={path.startsWith("/albums") ? "outlined" : "text"}
						to="/albums"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<CollectionsBookmarkIcon />}>
						Albums
					</Button>

				</Toolbar>
			</AppBar>
		</Box>
	);
};