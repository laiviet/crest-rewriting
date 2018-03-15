<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="javax.print.attribute.standard.Severity" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<html>
<head>
    <title>rewriting.Article rewriting system</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/style.css">
    <link rel='shortcut icon' type='image/x-icon' href="img/jaistlogo.jpg">
    <script src="http://code.jquery.com/jquery-2.2.4.min.js"
            integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
    <script src="js/app.js"></script>
    <script>
        $(document).ready(function () {
            $('textarea#goldStandard').on("keydown", function () {
                $('button#save_btn').show();
            });
        });
    </script>
</head>
<body>
<div style="justify-content: center;">
    <div class="logo">
        <img id="logoimg" src="img/logo.jpg">
        <h1>Article rewriting system</h1>
    </div>
    <div id="content">

        <table>
            <tr>
                <td>
                    <p id="instruction">
                        Please select the article.
                    </p>
                </td>
                <td>

                    <%
                        ArrayList<String> ids = (ArrayList<String>) request.getAttribute("ids");
                        if (ids != null) {
                    %>
                    <select id='article' onchange="request()">
                        <option id="ignore">Choose an article</option>
                        <%for (String e : ids) {%>
                        <option><%= e%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <%
                    } else {
                    %>
                    <p>No available article.</p>
                    <%
                        }
                    %>

                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <h4>Article sentence</h4>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea id="txtContent" disabled="disabled" rows="5" cols="100"></textarea>
                </td>
            </tr>
            <tr id="result">
                <td>
                    <h4>AMR graph</h4>
                </td>
                <td>
                    <h4>Rewrited sentences</h4>
                </td>
            </tr>
            <tr>
                <td>
                    <textarea id="txtAmr" disabled="disabled" rows="20" cols="50"></textarea>
                </td>
                <td>
                    <textarea id="txtRewrite" disabled="disabled" rows="20" cols="50"></textarea>
                </td>
            </tr>

        </table>
    </div>
    <h4 id="footer">Copyright by Japan Advanced Institute of Science and Technology - Nguyen Lab - 2016 Contact:
        nguyen-laboratory@googlegroups.com</h4>
</div>
</body>
</html>
