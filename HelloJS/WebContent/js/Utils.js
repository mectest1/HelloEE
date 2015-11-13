var Utils = (function Utils(){
    var CHAR_UPPERCASE = /[A-Z]/;
	var CSS_ID = /[#]/;
	var CSS_CLASS = /[.]/;
	/**
	 * 
	 * ref: https://developer.mozilla.org/en-US/docs/AJAX/Getting_Started
	 * ref2: https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest
	 * @param {type} url the request URL;
	 * @param {type} successFunc func(XMLHttpRequest xhr){..}
	 * @param {type} failedFunc func(XMLHttpRequest xhr){..}
	 * @param {String} responseType responseType for returned content, default to "text"
	 * @returns {undefined}
	 */
    function ajax(/*String*/ url, 
			/*function(XMLHttpRequest xhr)*/ successFunc,
			/*function(XMLHttpRequest xhr)*/ failedFunc,
			/*String*/ responseType
			){
		var xhr = newXMLHttpRequest();
	//	xhr.onload = function(){};
	//	xhr.addEventListener("progress", updateProgress, false);
	//	xhr.addEventListener("load", transferComplete, false);
	//	xhr.addEventListener("error", transferFailed, false);
	//	xhr.addEventListener("abort", transferCanceled, false);
		xhr.onreadystatechange = function(){
			if(4 !== xhr.readyState){	//complete
				return;
			}

			if(200 === xhr.status){ //request response all good
				if(successFunc){
					successFunc(xhr);
				}else{
		//		    console.log("request succceed: responseText: " + xhr.responseText);
				}
			}else{
				if(failedFunc && "function" == typeof failedFunc){
					failedFunc(xhr);
				}else{
		//		    console.log("Error occured during XMLHttpRequeset request.");
				}
			}
		};
		xhr.open("GET", url, true);
		if(responseType){
			xhr.responseType = responseType;
		}
		xhr.send(null);
    }
    
	/**
	 * Use Promise to send Ajax request, and this Promise will resolev to/reject with
	 * the responseText or responseXML, depending on the (optional) second parameter;
	 * @param {type} url the url to send ajax request to;
	 * @param {type} responseType set it to "XML" to get responseXML, or else get responseText back;
	 * @returns {Promise|String} a Promise that resolves to/rejects with responseText or responseXML;
	 */
	function ajaxPromise(/*String*/ url, /*String*/responseType){
		if(!window.Promise){
			return "Promise not supported in current Browser";
		}
		return new Promise(function (resolve, reject){
			var xhr = newXMLHttpRequest();
			xhr.open(url, true);
			xhr.send();
			xhr.onload = function(){
				if(200 === this.status){
					if("XML" === responseType || "xml" === responseType){
						resolve(this.responseXML);
					}else{
						resolve(this.responseText);
					}
				}else{
					reject(this.statusText);
				}
			};
			xhr.onerror = function(){
				reject(this.statusText);
			};
		});
	}
    function newXMLHttpRequest(){
		if(window.XMLHttpRequest){
			return new XMLHttpRequest();
		}else if(window.ActiveXObject){
			try{
				return new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				try{
					return new ActiveXObject("Msxml2.XMLHTTP");
				}catch(e2){}
			}
		}
    }
	
	/**
	 * 
	 * @param {type} ele the HTMLElement that got attribute of "data-attr-name";
	 * @param {type} attributeName format like "attrName", "attr-name", "data-attr-name", etc;
	 * @returns data set value
	 */
	function getDatasetAttr(/*HTMLElement*/ ele, /*String*/ attributeName){
		if(-1 < attributeName.indexOf('-')){
			if(0 === attributeName.indexOf("data-")){
				return ele.getAttribute(attributeName);
			}else{
				return ele.getAttribute("data-" + attributeName);
			}
		}
		if(ele.dataset){
			return ele.dataset[attributeName];
		}else{
			return ele.getAttribute("data-" + getDatasetAttributeName(attributeName));
		}
	}
	
	/**
	 * INPUT: "attributeNameLikeThis"
	 * OUTPUT: "data-attribute-name-like-this"
	 * @param {type} camelAttributeName
	 * @returns get original "data-*" name
	 */
	function getDatasetAttributeName(/*String*/ camelAttributeName){
		var retval = "";
		var char = "";
		for(var i = 0; i < camelAttributeName.length; ++i){
			char = camelAttributeName.charAt(i);
			if(CHAR_UPPERCASE.test(char)){
				retval += "-";
				char = char.toLowerCase();
			}
			retval += char;
		}
		
		return retval;
	}
	
	//---------------------------------------------------------
	/**
	 * To simulate Promise object in ES6;
	 * @param {type} executor function(resolve, reject)
	 * @returns a PromiseDIY object
	 */
	function PromiseDIY(/*function(resolve, reject)*/executor){	
		//Only allow constructor call
		if(Object.getPrototypeOf(this) !== PromiseDIY.prototype){
			throw new TypeError("Utils.PromiseDIY Should be used through constructor call.");
		}
		//or try this:
		//var obj = Object.create(PromiseDIY.prototype);

		var isFinished = false;	//if current executor task has finished;
		var isFailed = false;	//if current executor task has failed during execution
		var retval;				//fulfillment value from resolve() or reject(), default to undefined;
//		var succFunc = null;
//		var failedFunc = null;
		
		var thenFuncs = [];		//thenable functions;
		var taskException = [];	//exceptions during task execution;
		var thenSuccException = [];	//exceptions during thenable succFunc execution
		var thenFailedException = [];	//exception during thenable failedFunc execution

		var SLEEP_TIME = 1000;	//may need to set it to "1" for better response time;

		function isPromiseFullfilled(){
			return isFinished || isFailed;
		}
		function resolve(value){
			if(!isPromiseFullfilled()){		//only accept the first resolve();
				isFinished = true;
				retval = value;
			}
		}
		function reject(value){
			if(!isPromiseFullfilled()){		//only accept the first reject();
				isFailed = true;
				retval = value;
			}
		}

		function tryExecute(resolve, reject){
			var executed = false;
			var inter = setInterval(function executeTask(){
				if(!executed){
					executed = true;
					try{
						executor(resolve, reject);
					}catch(e){
						isFailed = true;
						taskException.push(e);
					}
				}
				if(isFinished || isFailed){
					clearInterval(inter);
					returnResult();
					return;
				}
			}, SLEEP_TIME);
			return inter;
		}
		
//		function tryThen(){
//			var inter = setInterval(function executeThen(){
//				if(isFinished || isFailed){
//					clearInterval(inter);
//					returnResult();
//				}
//			}, SLEEP_TIME);
//			return inter;
//		}

		function returnResult(){
			var succFunc = null;
			var failedFunc = null;
			for(var i = 0; i < thenFuncs.length; ++i){
				succFunc = thenFuncs[i].succFunc;
				failedFunc = thenFuncs[i].failedFunc;
				if(isFinished && succFunc && "function" === typeof succFunc){
					try{
						succFunc(retval);
					}catch(e){
						thenSuccException.push(e);
					}
				}
				if(isFailed && failedFunc && "function" === typeof failedFunc){
					try{
						failedFunc(retval);
					}catch(e){
						thenFailedException.push(e);
					}
				}
			}
		}
		
		//tryExecuteE() should get executed immediately when PromiseDIY get constructed
		//instead of deferred its execution to then();
		tryExecute(resolve, reject);
		//obj.then = fucntion then(..)
		this.then = function then(/*function(value)*/ succFunc, /*function(value)*/ failedFunc){
//			succFunc = succ;
//			failedFunc = failed;
			
//			tryThen();
//			if(isFinished || isFailed){
//				returnResult();
//			}
//			else{
//				tryExecute(resolve, reject);	
//			}

			thenFuncs.push({succFunc: succFunc, failedFunc: failedFunc});
			return this;
		};
		
		
		//return obj;
	}
	
	
	//========================================
	//
	// PromiseUtils.requestPromiseText(String url): request an URL through Ajax and reutnr a Promise;
	// 
	//
	//
	//
	//
	//========================================
	//----------------------------------------
	/**
	 * ES6 Promise Utilities;
	 * @type Function|Utils.Utils_L265.UtilsAnonym$3
	 */
	var PromiseUtils = function(){
		
		/**
		 * Request ajax text through ES6 Promise mechanism;
		 * @param {type} url the URL to request Ajax response
		 * @returns {Promise} a Promise that resolves to the Ajax response text;
		 */
		function requestPromiseText(/*String*/ url){
			return new Promise(function(resolve, reject){
				//the ajax() callback should be our promise's 
				//resolve() function;
				ajax(url, function resolveResult(xhr){
					resolve(xhr.responseText);
				});
			});
		}
		return {
			request: requestPromiseText
		};
	}();
	
	
	//========================================
	//
	// TextUtils.wrappedText(HTMLTextareElement): calculate wrapped <textarea> content;
	//
	//
	//
	//
	//========================================
	//----------------------------------------
	var TextUtils = function TextUtils(){
	
		/**
		 * Wrap &lt;textarea/&gt; content;
		 * @param element HTMLTextAreaElement
		 * @return the final rows that as shown in browser;
		 */
		var wrappedTextAreaContent = function(/*HTMLTextAreaElement*/ element){
			var v = element.value;
			var rows = element.rows;
			var cols = element.cols;
			var ar = v.replace(/\r/g, '').split("\n");
			var hasErr = false;
			var totalLen = 0;
			var maxLen = rows * cols;
			var CRNL = "\r\n";
			var isBreakWord = true;
			if(element.style.wordWrap != "break-word"){
				isBreakWord = false;
				var ar2 = [];
				var SPACE = " ";
				var spaceRE = /\s/;
				var charRE = /\w/;
				for(var i = 0; i < ar.length; ++i){
					if(0 < i){
						ar2[ar2.length-1] += CRNL;
					}
					var lineStr = ar[i];
					if(null == lineStr || cols >= lineStr.length){
						ar2.push(lineStr);
						continue;
					}
					while(lineStr.length > 0){
						var expectedLine = lineStr.substr(0, cols);
						if(cols >= lineStr.length){
							ar2.push(expectedLine);
							break;
						}//else cols == expectedLine.length
						var spaceIndex = expectedLine.lastIndexOf(SPACE);
						var lastChar = expectedLine.charAt(expectedLine.length - 1);
						var spacesRE = /^\s+$/;
						if(-1 >= spaceIndex || spacesRE.test(expectedLine)){	//no space, or filled with spaces
							ar2.push(expectedLine);
							lineStr = lineStr.substring(cols);
							continue;
						}//else there is space in this line;
						if(spaceRE.test(lastChar)){
							//last character is space, 
							//and we still need to count for the following multiple continuing spaces
							lineStr = lineStr.substring(cols);
							var firstSpaceIndex = lineStr.indexOf(SPACE);
							if(0 > firstSpaceIndex || cols <= firstSpaceIndex ){//leading space not in the following line
								ar2.push(expectedLine);
							}else{	//leading space in the following line
								var strToFirstSpace = lineStr.substring(0, firstSpaceIndex); //in the nextLine
								if(spacesRE.test(strToFirstSpace) || 0 == firstSpaceIndex){
									//multiple continuing spaces in the next line, may retract further;
									var trimmedExpectedLine = expectedLine.trim();
									if(0 >= trimmedExpectedLine.length){
										ar2.push(expectedLine);
									}else{
										var trimmedStartIndex = expectedLine.indexOf(trimmedExpectedLine);
										var trimmedLastSpaceIndex = trimmedExpectedLine.lastIndexOf(SPACE);
										var lastSecondSpaceIndex = trimmedStartIndex + trimmedLastSpaceIndex;

										if(-1 < lastSecondSpaceIndex){
											ar2.push(expectedLine.substring(0, lastSecondSpaceIndex + 1));
											lineStr = expectedLine.substring(lastSecondSpaceIndex + 1) + lineStr;
										}else{ //only when trimmedStartIndex: 0 and trimmedLastSpaceIndex: -1;
											ar2.push(expectedLine);
										}
									}

								}else{//not multiple continuing spaces: only one space, or looks like /\w+ /
									ar2.push(expectedLine);
								}
							}
							continue;
						}//else it's a Latin character, a.k.a part of a whole word

						ar2.push(expectedLine.substring(0, spaceIndex + 1));
						lineStr = expectedLine.substring(spaceIndex + 1) + lineStr.substring(cols);

					}
				}
				ar = ar2;
			}
			var b = [];
	//		var valueLength = v.length;
	//		if(valueLength > maxLen){
	//			hasErr = true;
	//		}
			var arLen = 0;
			if(null != ar && 0 < ar.length){
				arLen = ar.length;
			}
			for(var i = 0; i < arLen; ++i){
				var lineStr = ar[i];
				var newLine = '';
				//var endsWithCRNL = lineStr.endsWith(CRNL);	//still experimental for now
				var endsWithCRNL = -1 < lineStr.lastIndexOf(CRNL);
				if(endsWithCRNL){
					lineStr = lineStr.substring(0, lineStr.lastIndexOf(CRNL));
				}
				for(var j = 0; j < lineStr.length; ++j){
					var ch=lineStr.charAt(j); 
					var chLen = getByteLength(ch + "");
					if(totalLen + chLen > maxLen)
					{
						hasErr = true; 
//						appendEnter = false;
						break;
					}
					totalLen = totalLen * 1 + chLen;
					newLine += ch;

					if(newLine.length >= cols){
						b.push(newLine);
						newLine = '';
					}
				}
				if(0 < newLine.length || 0 >= lineStr.length){
					if(!hasErr){
						if(isBreakWord || (!isBreakWord && endsWithCRNL)){
							newLine += CRNL;
						}
					}
					b.push(newLine);
				}
				if(hasErr){
					break;
				}
			}
			ar = b;

			return{
				displayRows: ar,
				hasError: hasErr
			};
		};


		return {
			wrapTextAreaContent: wrappedTextAreaContent,	 //Old API
			wrappedTextAreaContent : wrappedTextAreaContent
		};
	}();
	
	//---------------------------
	
	//========================================
	// LayoutUtils: Page layout related utilities;
	// 
	//	.wireHeaderWithContainer: wire the specified header with its container tag,
	//		after they're wired up, you can click this headerTag to expand&collapse 
	//		the container tag.
	//		
	//	.insertContentIntoContainer: insert content as specified with url into container.
	//
	//
	//
	//
	//========================================
	//----------------------------------------
	var LayoutUtils = (function LayoutUtils(){
		var HEADERS = ["h1", "h2", "h3", "h4", "h5", "h6"];
		var HEADER_STYLES_AND_SCRIPTS =  ["link", "script", "style"];
		var ELEMENT_NODE = 1;
		var EXPAND_HEADER_STYLE_CLASS = "expand-headers";
		var EXPAND_EVENT = "click";
		var IS_INITIAL_COLLAPSE = "initialCollapse";
		/**
		 * This method is used to wire first header tag with it's container tag 
		 * to make it an expand/shrink controller.
		 * 
		 * 
		 * @param {String} containerTagName
		 * @param {String} headerTagName 
		 * @param {Object} wireOptions:
		 *	{Boolean} initialCollapse should the container tag set to collapse state initially?
		 * @returns {undefined}
		 */
//		function wireHeaderWithContainer(/*String*/ headerTagName, /*String*/ containerTagName, 
		function wireContainerWithHandler(/*String*/ containerTagName, /*String*/ headerTagName, 
			/*Object*/ wireOptions
		){
			var containerTags = $(containerTagName);
			var initialCollapse = wireOptions && wireOptions[IS_INITIAL_COLLAPSE];
			for(var i = 0; i < containerTags.length; ++i){
				var containerTag = containerTags.item(i);
				var childNode = containerTag.firstChild;
				//LOOP_WHILE:
				while(childNode){
					if(isThisTag(headerTagName, childNode)){
						//ref: https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/style
						//ref2: https://developer.mozilla.org/en-US/docs/Web/API/CSSStyleDeclaration
						//ref3: https://developer.mozilla.org/en-US/docs/Web/CSS/cursor
						
						//childNode.style.cursor = "pointer";
						addStyleClass(childNode, EXPAND_HEADER_STYLE_CLASS);
						childNode.addEventListener(EXPAND_EVENT, onClickToExpandOrCollapseContainer(childNode, containerTag, !initialCollapse));
						if(initialCollapse){
							showOnlyOneChildNode(containerTag, childNode);
						}
						break;	//LOOP_WHILE
					}
					childNode = childNode.nextSibling;
				}
			}
		}
		
		
		function onClickToExpandOrCollapseContainer(/*HTMLElement*/ headerTag, /*HTMLElement*/ containerTag, /*Boolean*/ initialExpanded){
			//var expanded = true;
			var expanded = !!initialExpanded;
			return function expandCollapseContainer(event){
				if(expanded){
					showOnlyOneChildNode(containerTag, headerTag);
					expanded = false;
				}else{
					showAllChildNodes(containerTag, headerTag);
					expanded = true;
				}
			};
		}
		
		function isThisTag(/*String*/ headerTagName, /*String*/ childNode){
			return headerTagName.toUpperCase() === childNode.nodeName.toUpperCase();
		}
		
		function showOnlyOneChildNode(/*HTMLElement*/ containerTag, /*HTMLElement*/ hilightTag){
			var childNode = containerTag.firstChild;
			while(childNode){
				if(ELEMENT_NODE !== childNode.nodeType || childNode === hilightTag){
					childNode = childNode.nextSibling;
					continue;
				}
				hideElement(childNode);
				childNode = childNode.nextSibling;
			}
		}
		
		function showAllChildNodes(/*HTMLElement*/ containerTag, /*(Optinoal) HTMLElement*/ hilightTag){
			var childNode = containerTag.firstChild;
			while(childNode){
				if(ELEMENT_NODE !== childNode.nodeType || childNode === hilightTag){
					childNode = childNode.nextSibling;
					continue;
				}
				showElement(childNode);
				childNode = childNode.nextSibling;
			}
		}
		/**
		 * Add styleClass into current element's classList;
		 * 
		 * ref: https://developer.mozilla.org/en-US/docs/Web/API/Element/classList
		 * ref2: https://developer.mozilla.org/en-US/docs/Web/API/Element/className
		 * @param {type} ele
		 * @param {type} styleClassName
		 * @returns {undefined}
		 */
		function addStyleClass(/*HTMLElement*/ ele, /*String*/ styleClassName){
			if(!ele){
				return;
			}
			if(ele.classList){
				ele.classList.add(styleClassName);
			}else{
				ele.className += " " + styleClassName;
			}
		}
		function hideElement(/*HTMLElement*/ ele){
			if(!ele){
				return;
			}
			ele.style.display = "none";
		}
		
		function showElement(/*HTMLElement*/ ele){
			if(!ele){
				return;
			}
			ele.style.display = "block";
		}
		
		function isDisplayed(/*HTMLElement*/ ele){
			if(!ele){
				return;
			}
			var retval = ele.style.display;
			return "none" !== retval;
		}
		
		
//		/**
//		 * Request a specified URL and get the returned result as XML Document, 
//		 * then return node or nodeList with the specified nodeId or any CSS selector;
//		 * e.g.: requestXMLContentNode("goods.html", "#main-content");
//		 * @param {type} url request document URL, e.g. a html document, or an XML file;
//		 * @param {type} nodeId teh specified nodeId to return, e.g.
//		 * @returns {undefined}
//		 */
		function requestXMLContentNode(/*String*/ url, /*String*/nodeId, 
				/*function(Node|NodeList)*/ succCallback, /*function (String)*/ failedCallback){
			ajax(url, function succFunc(xhr){
				var responseXML = xhr.responseXML;
				var selectedNode = QueryUtils.select(responseXML, nodeId);
				succCallback && succCallback(selectedNode);
			}, function failedFunc(xhr){
//				returnedXML = document.createElement("error");
//				var errorInfo = document.createTextNode(xhr.statusText);
//				returnedXML.appendChild(errorInfo);
				var responseText = xhr.responseText;
				failedCallback && failedCallback(responseText);
			}, "document");
			
//			var p = new Promise(function(resolve, reject){
//				ajax(url, function ajaxSucc(xhr){
//					resolve(xhr.responseXML);
//				}, function ajaxFailed(xhr){
//					reject(xhr.responseXML);
//				});
//			});

//			var p = ajaxPromise(url, "XML");
//			p.then(succCallback, failedCallback);
		}
		
		/**
		 * Requeset Content from specified URL and get Node|NodeList from 
		 * it as specified with nodeId, then insert the returned result 
		 * as child node into containerTag.
		 * 
		 * USAEG CASE:
		 *  Utils.LayoutUtils.insertContentIntoContainer($("#foo"), "bar.xml", "#main-content", 
		 *				{
		 *					"appendHeaderInfo": true
		 *				}
		 *			);
		 * @param {type} containerTag
		 * @param {type} url
		 * @param {type} contentNodeId
		 * @param {Object} insertOptions: 
		 *		.appendHeaderInfo: false(default)|true:	whether to append tags like
		 *			&lt;link&gt;, &lt;style&gt;, &lt;script&gt; in &lt;header&gt; into
		 *			specified containerTag.
		 * @returns {undefined}
		 */
		function insertContentIntoContainer(/*HTMLElement*/ containerTag, 
					/*String*/ url, /*String*/ contentNodeId,
					/*(Optional) Object*/ insertOptions){
						
			var INSERT_OPTIONS_APPEND_HEADER_INFO = "appendHeaderInfo";
			
			
			requestXMLContentNode(url, contentNodeId, appendContentToContainer);
			function appendContentToContainer(/*Node|NodeList*/ contentNode){
				var ownerDocument = null;
				if(contentNode.length && 0 < contentNode.length 
						&& contentNode.item && contentNode.item(0)){
					for(var i = 0; i < contentNode.length; ++i){
						containerTag.appendChild(contentNode.item(i));
//						appendAllChildNodes(containerTag, contentNode.item(i));
						if(!ownerDocument){
							ownerDocument = contentNode.ownerDocument;
						}
					}
				}else{
//					containerTag.appendChild(contentNode);
					appendAllChildNodes(containerTag, contentNode);
					ownerDocument = contentNode.ownerDocument;
				}
				
				//
				if(insertOptions){
					var appendHeaderInfo = insertOptions[INSERT_OPTIONS_APPEND_HEADER_INFO];
					if(appendHeaderInfo){
						var contentHeaderNode = ownerDocument.getElementsByTagName("head").item(0);
						var containerOwner = containerTag.ownerDocument;
						var containerHeaderNode = containerOwner.getElementsByTagName("head").item(0);
						appendAllChildNodes(containerHeaderNode, contentHeaderNode, 
							{
								"childNodeTypeWhiteList": [ELEMENT_NODE],
								"childNodeNameWhiteList": HEADER_STYLES_AND_SCRIPTS
							}
						);
					}
				}
			}
		}
		
		
		/**
		 * Append all child nodes from fromParentNode into toContainer;
		 * @param {type} toContainer
		 * @param {type} fromParentNode
		 * @param appendOptions:
		 *				.childNodeNameWhiteList: Array
		 *				.childNodeTypeWhiteList: Array
		 * @returns {undefined}
		 */
		function appendAllChildNodes(/*Node*/toContainer, /*Node*/ fromParentNode,
				/*Object*/ appendOptions
				){
			var APPEND_OPTIONS_CHILD_NODE_TYPE_WHITE_LIST = "childNodeTypeWhiteList";
			var APPEND_OPTIONS_CHILD_NODE_NAME_WHITE_LIST = "childNodeNameWhiteList";
			var SCRIPT_NODE_NAME = "script";
			var SCRIPT_TYPE = "text/javascript";
			
			if(!(toContainer && fromParentNode)){
				return;
			}
			
			var nodeTypeWhiteList = appendOptions && appendOptions[APPEND_OPTIONS_CHILD_NODE_TYPE_WHITE_LIST];
			var nodeNameWhiteList = appendOptions && appendOptions[APPEND_OPTIONS_CHILD_NODE_NAME_WHITE_LIST];
			var childNode = fromParentNode.firstChild;
			var nextSibling;
			while(childNode){
				nextSibling = childNode.nextSibling;
				if(checkNodeValidity(childNode, nodeTypeWhiteList, nodeNameWhiteList)){
					if(SCRIPT_NODE_NAME.toUpperCase() === childNode.nodeName.toUpperCase()){
						var scriptNode = document.createElement(SCRIPT_NODE_NAME);
						scriptNode.type = SCRIPT_TYPE;
						childNode.src && (scriptNode.src = childNode.src);
						childNode.textContent && (scriptNode.textContent = childNode.textContent);
						toContainer.appendChild(scriptNode);
					}else{
						toContainer.appendChild(childNode);	
					}
				}
				childNode = nextSibling;
			}
		}
		
		/**
		 * Check whether the specified node has passed all the white list test;
		 * @param {type} node node to be tested;
		 * @param {type} nodeTypeWhiteList node type white list;
		 * @param {type} nodeNameWhiteList node name white list;
		 * @returns {Boolean}
		 */
		function checkNodeValidity(/*Node*/node, /*Array*/ nodeTypeWhiteList, /*Array*/ nodeNameWhiteList){
			var nodeNameChecked = false;
			var nodeTypeChecked = false;
			
			var nodeType;
			var nodeName;
			//
			if(!nodeTypeWhiteList || 0>= nodeTypeWhiteList.length){
				nodeTypeChecked = true;
			}else{
				for(var i = 0; i < nodeTypeWhiteList.length; ++i){
					nodeType = nodeTypeWhiteList[i];
					if(nodeType && node.nodeType
						&& node.nodeType === nodeType
					){
						nodeTypeChecked = true;
						break;
					}
				}
			}
			if(!nodeTypeChecked){
				return nodeTypeChecked;
			}
			
			
			if(!nodeNameWhiteList || 0 >= nodeNameWhiteList.length){
				nodeNameChecked = true;
			}else{
				for(var i = 0; i < nodeNameWhiteList.length; ++i){
					nodeName = nodeNameWhiteList[i];
					if(nodeName && node.nodeName
						&& node.nodeName.toUpperCase() === nodeName.toUpperCase()
					){
						nodeNameChecked = true;
						break;
					}
				}
			}
			if(!nodeNameChecked){
				return nodeNameChecked;
			}
			
			
			return nodeTypeChecked && nodeNameChecked;
		}
		return {
			wireContainerWithHandler: wireContainerWithHandler,
//			requestXMLContentNode: requestXMLContentNode
			insertContentIntoContainer: insertContentIntoContainer
		};
	})();
	
	
	/**
	 * StyleUtils: CSS Style related utilities;
	 * @returns {}
	 */
	var StyleUtils = function StyleUtils(){
		
//		function isCSSTag(){
//			
//		}
//		
//		function isCSSClass(/*String*/ name){
//			
//		}
//		
//		function isCSSId(/*String*/ name){
//			
//		}
	}();
	
	
	var QueryUtils = function QueryUtils(){
		
		function select(/*Node*/ node, /*String*/ selector){
			if(!node){
				return null;
			}else if(!selector){
				return node;
			}
			
			var dotIndex = selector.indexOf(".");
			var hashIndex = selector.indexOf("#");
			
			if(node.querySelectorAll){ //&& node.querySelector()
				
				var retval;
				if(-1 < hashIndex){
					retval = node.querySelector(selector);
				}else{
					retval = node.querySelectorAll(selector);
				}
				
				return retval;
			}
			
			
			if(-1 < hashIndex){
				var idName = selector.substr(1 + hashIndex);
				return node.getElementById(idName);
			}else if(-1 < dotIndex){
				var className = selector.substr(1 + dotIndex);
				return node.getElementsByClassName(className);
			}else{
				return node.getElementsByTagName(selector);
			}
		}
		
		return {
			select: select
		};
	}();
    //---------------------------------------
    function $(/*String*/ name){
		return QueryUtils.select(document, name);
    }
	var getElement = $;
    
	
	
	//
	
	var retval = {
		ajax: ajax,
		ajaxPromise: ajaxPromise,
		getDatasetAttr: getDatasetAttr,
		PromiseDIY: PromiseDIY,
		PromiseUtils: PromiseUtils,
		TextUtils: TextUtils,
		LayoutUtils: LayoutUtils
    };
    //--------------------------
    //-Register global functions
    //--------------------------
	var old_$ = window.$;	//reserver the former value of window.$
    window.$ = getElement;
    //--------------------------
    return retval;
})();