package com.eheart.service.impl;

import com.eheart.repository.UserRepository;
import com.eheart.service.UploadService;
import com.eheart.service.UserService;
import com.eheart.service.mapper.UserMapper;
import com.eheart.service.dto.UploadDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

/**
 * Created by lichen on 2016/10/15.
 */
@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    private final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Value("${eheart.upload.path}")
    private String user_upload_file_root_path;

//    @Inject
//    private UserService userService;
//
//    @Inject
//    private UserRepository userRepository;
//
//    @Inject
//    private UserMapper userMapper;

    @Override
    public UploadDTO saveFile(MultipartFile file) throws IOException {

        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
            .toLowerCase();

        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extName;
        String rootPath = user_upload_file_root_path;

        String directory = rootPath;

        log.debug("new saving file folder -> " + directory);

        File newSavedFile = new File(directory);

        if (!newSavedFile.exists()) {
            newSavedFile.mkdir();
        }
        File uploadFile = new File(directory + "/" + fileName);
        log.debug("new saving file folder 3 -> " + uploadFile.getPath());
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        //add owners permission
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        //add group permissions
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        //add others permissions
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);

        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        IOUtils.copy(file.getInputStream(), fileOutputStream);

        try {
            Files.setPosixFilePermissions(Paths.get(uploadFile.getPath()), perms);
        } catch (UnsupportedOperationException e) {
            log.debug("windows throw exception here diff from linux.");
        }

        UploadDTO uploadDTO = new UploadDTO(null, fileName);
        log.debug(" uploadDTO file: " + uploadDTO.getFile());

        return uploadDTO;
    }

//    @Override
//    public UploadDTO importUsersByTrip(MultipartFile file, Long tripId) throws IOException {
//
//        File uploadFile = null;
//        UploadDTO uploadDTO;
//        StringBuilder message = new StringBuilder();
//
//        try{
//            String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "_"
//                + file.getOriginalFilename();
//            String rootPath = user_upload_file_root_path;
//
//            String directory = rootPath;
//            uploadFile = new File(directory + "/" + fileName);
//
//
//            FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
//            IOUtils.copy(file.getInputStream(), fileOutputStream);
//
//            log.debug("uploaded users file" + uploadFile.getPath());
//
//            // start to import users
//
//            List<User> users = new ArrayList<>();
//            Map<String, TeamDTO> teamDTOMap =  new HashMap<String, TeamDTO>();
//            Set<String> authorities = new HashSet<>();
//            authorities.add("ROLE_USER");
//
//            if (fileName.contains(".xls")){
//                uploadFile = new File(directory + "/" + fileName + ".csv");
//                convertXlsToCsv(file, uploadFile);
//            } else if (fileName.contains(".csv")){
//
//            } else {
//                uploadDTO = new UploadDTO(null, uploadFile.getName());
//                uploadDTO.getMessages().add("error file format!");
//                return uploadDTO;
//            }
//
//            CsvMapper mapper = new CsvMapper();
//
//            CsvSchema schema = CsvSchema.builder()
//                .addColumn("login")
//                .addColumn("firstName")
//                .addColumn("email")
//                .addColumn("teamName")
//                .addColumn("attribute")
//                .addColumn("leader")
//                .build();
//
//            MappingIterator<ManagedUserVM> it = mapper.readerFor(ManagedUserVM.class).with(schema).readValues(uploadFile);
//
//            TripDTO tripDTO = tripService.findOne(tripId);
//            //新手线路 个人线路 不清空原有线路中的team
//            if (tripId.longValue() != 1L && tripId.longValue() != 3L){
//                tripDTO.getHasTeams().clear();
//            }
//
//            Optional <User> userOptional;
//            User user;
//            while (it.hasNext()) {
//                ManagedUserVM userVM = it.next();
//                if (userVM.getLogin().contains("login")){
//                    // remove the header
//                    continue;
//                }
//                // and voila, column values in an array. Works with Lists as
//                userOptional = userRepository.findOneByLogin(userVM.getLogin());
//                if (!userOptional.isPresent()){
//                    int length = userVM.getLogin().length();
//                    if (length < 4){
//                        userVM.setPassword(userVM.getLogin());
//                    }else{
//                        userVM.setPassword(userVM.getLogin().substring(length - 4));
//                    }
//                    userVM.setAuthorities(authorities);
//                    user = userService.createUser(userVM);
//                    log.info("success to save user: " + user.getFirstName());
//
//                }else{
//                    user = userOptional.get();
//                    log.info("user already exist: " + user.getFirstName());
//                }
//
//                users.add(user);
//
//                TeamDTO teamDto = teamService.findOneByNameInTrip(userVM.getTeamName(), tripId);
//
//                if (!teamDTOMap.containsKey(userVM.getTeamName())){
//                    // create team
//                    if (teamDto == null){
//                        teamDto = new TeamDTO();
//                        teamDto.setName(userVM.getTeamName());
//
//                    }else{
//                        // exist team, want to clear users in the team.
//                        teamDto.getHasMembers().clear();
//                    }
//                    teamDto.setAttribute(userVM.getAttribute());
//                    teamDto.getHasMembers().add(this.userMapper.userToUserDTO(user));
//
//                    teamDTOMap.put(teamDto.getName(), teamDto);
//                }else{
//                    // add user to team
//                    teamDTOMap.get(userVM.getTeamName()).getHasMembers().add(userMapper.userToUserDTO(user));
//                }
//                //set leader
//                if (userVM.getLeader() != null && userVM.getLeader().equalsIgnoreCase("Y")){
//                    teamDTOMap.get(userVM.getTeamName()).setLeaderId(user.getId());
//                }
//
//            }
//            for (TeamDTO teamDto:teamDTOMap.values()
//                ) {
//                teamDto = teamService.save(teamDto);
//                log.info("success to save team: " + teamDto.getName());
//                // add team to trip
//                tripDTO.getHasTeams().add(teamDto);
//                message.append("<br><br>").append("team: ").append(teamDto.getName());
//                for (UserDTO userDTO : teamDto.getHasMembers()){
//                    message.append("<br>").append("member: ").append(userDTO.getLogin())
//                    .append("  ").append(userDTO.getFirstName()).append(StringUtils.trimToEmpty(userDTO.getLastName()));
//                }
//
//            }
//
//            tripService.save(tripDTO);
//
//
//        }catch(Exception e){
//            uploadDTO = new UploadDTO(null, uploadFile.getName());
//            uploadDTO.getMessages().add(e.toString());
//            return uploadDTO;
//        }
//
//        uploadDTO = new UploadDTO(null, uploadFile.getName());
//        uploadDTO.getMessages().add("success");
//        uploadDTO.getMessages().add(message.toString());
//
//        return uploadDTO;
//    }
//
//    private void convertXlsToCsv(MultipartFile inputFile, File outputFile) {
//        try {
//            OutputStream os = (OutputStream) new FileOutputStream(outputFile);
//            String encoding = "UTF8";
//            OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
//            BufferedWriter bw = new BufferedWriter(osw);
//
//            WorkbookSettings ws = new WorkbookSettings();
////            ws.setLocale(new Locale("en", "EN"));
//            Workbook w = Workbook.getWorkbook(inputFile.getInputStream(), ws);
//
//            // Gets the sheets from workbook
////            for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++) {
//            Sheet s = w.getSheet(0);
//
//            Cell[] row = null;
//
//            // Gets the cells from sheet
//            for (int i = 0; i < s.getRows(); i++) {
//                row = s.getRow(i);
//
//                if (row.length > 0) {
//                    // login is empty, end it.
//                    if (StringUtils.isEmpty(row[0].getContents().trim())){
//                        break;
//                    }
//                    bw.write(row[0].getContents());
//                    for (int j = 1; j < row.length; j++) {
//                        bw.write(',');
//                        bw.write(row[j].getContents());
//                    }
//                }
//                bw.newLine();
//            }
////            }
//            bw.flush();
//            bw.close();
//        } catch (UnsupportedEncodingException e) {
//            System.err.println(e.toString());
//        } catch (IOException e) {
//            System.err.println(e.toString());
//        } catch (Exception e) {
//            System.err.println(e.toString());
//        }
//    }
}
