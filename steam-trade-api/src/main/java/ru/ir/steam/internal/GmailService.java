package ru.ir.steam.internal;

import com.google.common.base.Splitter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.imap.IMAPSClient;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailService {

    private static final Pattern searchLabelPattern = Pattern.compile("\\* SEARCH(( \\d+)+)");

    public String getCode(String username, String gmailImapAddress, String gmailAddress, String gmailLabel, String gmailPassword) throws IOException {
        IMAPSClient client = new IMAPSClient("SSL", true);
        client.addProtocolCommandListener(new PrintCommandListener(System.out, true));
        client.connect(gmailImapAddress);
        client.login(gmailAddress, gmailPassword);
        client.select("INBOX");
        client.search("UTF-8", " X-GM-LABELS " + gmailLabel);
        Matcher matcher = searchLabelPattern.matcher(client.getReplyString());
        while (matcher.find()) {
            List<String> list = Splitter.on(" ").splitToList(matcher.group(1).trim());
            String last = list.get(list.size() - 1);
            client.fetch(last, "BODY[TEXT]");
            Pattern pattern = Pattern.compile((username + ":([\\d||A-Z]{5})"));
//            String body = StreamEx.of(Splitter.on('\n').splitToList(client.getReplyString())).map(next -> next.trim().filter(_ >= ' ')).mkString
//            pattern.findFirstMatchIn(body) map {
//                message =>message.group(1)
//            }
        }

        return "";
    }

}
