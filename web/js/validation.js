/**
 * 表单验证和学院系联动脚本
 */

// 学院和系的数据结构
const collegeData = {
  "计算机学院": ["软件工程", "计算机科学与技术", "网络工程"],
  "电子信息学院": ["电子信息工程", "通信工程", "电子科学与技术"],
  "机械工程学院": ["机械设计制造及其自动化", "机械工程", "工业设计"]
};

/**
 * 页面加载完成后执行初始化
 */
window.onload = function () {
  // 初始化学院下拉框
  initCollegeSelect();

  // 绑定密码验证事件
  document.getElementById("password").addEventListener("blur", validatePassword);

  // 绑定表单提交事件
  document.getElementById("loginForm").addEventListener("submit", validateForm);
};

/**
 * 初始化学院下拉框
 */
function initCollegeSelect() {
  const collegeSelect = document.getElementById("college");
  const departmentSelect = document.getElementById("department");

  // 清空下拉框
  collegeSelect.innerHTML = "";

  // 添加默认选项
  let defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.text = "请选择学院";
  collegeSelect.appendChild(defaultOption);

  // 添加学院选项
  for (let college in collegeData) {
    let option = document.createElement("option");
    option.value = college;
    option.text = college;
    collegeSelect.appendChild(option);
  }

  // 添加学院变化事件监听器
  collegeSelect.addEventListener("change", function () {
    updateDepartmentSelect(this.value);
  });

  // 初始化系下拉框为空
  departmentSelect.innerHTML = "";
  defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.text = "请选择系";
  departmentSelect.appendChild(defaultOption);
}

/**
 * 根据选择的学院更新系的下拉框
 * @param {string} college - 选择的学院
 */
function updateDepartmentSelect(college) {
  const departmentSelect = document.getElementById("department");

  // 清空当前的系选项
  departmentSelect.innerHTML = "";

  // 添加默认选项
  let defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.text = "请选择系";
  departmentSelect.appendChild(defaultOption);

  // 如果选择了有效的学院，添加对应的系选项
  if (college && collegeData[college]) {
    collegeData[college].forEach(function (department) {
      let option = document.createElement("option");
      option.value = department;
      option.text = department;
      departmentSelect.appendChild(option);
    });
  }
}

/**
 * 验证密码是否同时包含数字和字母
 * @returns {boolean} - 验证结果
 */
function validatePassword() {
  const passwordInput = document.getElementById("password");
  const passwordValue = passwordInput.value;
  const passwordError = document.getElementById("passwordError");

  // 判断密码是否同时包含数字和字母的正则表达式
  const passwordPattern = /^(?=.*[0-9])(?=.*[a-zA-Z]).*$/;

  if (!passwordPattern.test(passwordValue)) {
    // 显示错误信息
    passwordError.textContent = "密码必须同时包含数字和字母";
    passwordError.style.color = "red";
    passwordInput.style.borderColor = "red";
    return false;
  } else {
    // 清除错误信息
    passwordError.textContent = "";
    passwordInput.style.borderColor = "";
    return true;
  }
}

/**
 * 表单提交时进行验证
 * @param {Event} event - 事件对象
 * @returns {boolean} - 验证结果
 */
function validateForm(event) {
  // 验证用户名
  const username = document.getElementById("username").value;
  if (!username) {
    alert("请输入用户名");
    event.preventDefault();
    return false;
  }

  // 验证密码
  if (!validatePassword()) {
    event.preventDefault();
    return false;
  }

  // 验证学院选择
  const college = document.getElementById("college").value;
  if (!college) {
    alert("请选择学院");
    event.preventDefault();
    return false;
  }

  // 验证系选择
  const department = document.getElementById("department").value;
  if (!department) {
    alert("请选择系");
    event.preventDefault();
    return false;
  }

  return true;
} 